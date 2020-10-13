package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.model.Role.USER;
import static ru.javawebinar.topjava.util.MealsUtil.getId;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    private AdminRestController controller;
    ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(AdminRestController.class);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("doPost");
        String action = request.getParameter("action");
        String userId = request.getParameter("userId");
        log.info("action {}", action);
        if(userId != null && !userId.isEmpty()) {
            SecurityUtil.setUserId(Integer.parseInt(userId));
            response.sendRedirect("meals");
        }
        else {
            User user = new User(null, request.getParameter("name"), request.getParameter("email"),
                    request.getParameter("password"), 2000, true, Collections.singleton(USER));
            String id = request.getParameter("id");
            log.info("id {}", id);
            if(id != null && !id.isEmpty()) {
                user.setId(Integer.parseInt(id));
            }
            log.info(user.isNew() ? "Create {}" : "Update {}", user);
            if (user.isNew()) {
                controller.create(user);
            } else {
                controller.update(user, user.getId());
            }
            response.sendRedirect("users");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet");
        String action = request.getParameter("action");
        log.info("action {}", action);

        switch (action == null ? "all" : request.getParameter("action")) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("users");
                break;
            case "create":
            case "update":
                final User user = "create".equals(action) ?
                        new User(null, "", "example@gmail.com", "", USER) : controller.get(getId(request));
                request.setAttribute("user", user);
                request.getRequestDispatcher("/userForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("users", controller.getAll());
                request.getRequestDispatcher("/users.jsp").forward(request, response);
                break;
        }
    }
}
