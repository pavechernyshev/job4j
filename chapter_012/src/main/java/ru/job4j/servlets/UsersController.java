package ru.job4j.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.logic.RoleService;
import ru.job4j.logic.SessionHelper;
import ru.job4j.logic.ValidateService;
import ru.job4j.models.Role;
import ru.job4j.models.User;
import ru.job4j.persistent.Store;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.function.Function;

public class UsersController extends HttpServlet {

    private final Map<String, Function<User, Boolean>> dispatch = new HashMap<>();
    private final Store validateService = ValidateService.getINSTANCE();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionHelper sessionHelper = new SessionHelper();
        User currentUser = sessionHelper.getCurrentUser(req, validateService);
        List<User> users = new LinkedList<>();
        if (currentUser.getRole().getId() == RoleService.ADMIN_ROLE_ID) {
            users = validateService.findAll();
        } else {
            users.add(currentUser);
        }
        req.setAttribute("users", users);
        req.getRequestDispatcher("/WEB-INF/views/UsersView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        this.initDispatch();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        File savedFile = null;
        Map<String, String> params = new HashMap<>();
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists() && !folder.mkdir()) {
                throw new FileUploadException("не возможно сохранить файл.");
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    File file = new File(folder + File.separator + item.getName());
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.get());
                        savedFile = file;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    params.put(item.getFieldName(), item.getString());
                }
            }
            String strId = params.get("id") == null ? "0" : params.get("id");
            int id = Integer.parseInt(strId);
            String name = params.get("name");
            String login = params.get("login");
            String email = params.get("email");
            String password = params.get("password");
            String role = params.get("role");
            User user = new User(id, name, login, email);
            user.setPassword(password);
            user.setRole(new Role(Integer.parseInt(role), ""));
            if (savedFile != null) {
                user.setPhotoId(savedFile.getName());
            }
            this.executeDispatch(params.get("action"), user);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        resp.sendRedirect(String.format("%s/list", req.getContextPath()));
    }

    private void initDispatch() {
        this.load("add", validateService::add);
        this.load("update", validateService::update);
        this.load("delete", (User user) -> { return validateService.delete(user.getId()); });
    }

    private void executeDispatch(String action, User user) {
        Function<User, Boolean> func = this.dispatch.get(action);
        if (func != null) {
            func.apply(user);
        }
    }

    public void load(String action, Function<User, Boolean> handle) {
        dispatch.put(action, handle);
    }
}
