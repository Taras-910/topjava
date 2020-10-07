package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_REP_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;
import static ru.javawebinar.topjava.util.TimeUtil.DATE_TIME_FORMATTER;
import static ru.javawebinar.topjava.util.TimeUtil.parseLocalDateTime;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealService service;

    public void init() {
        this.service = new MealService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String mealId = request.getParameter( "id");
        log.info("doPost id {}", mealId);
        LocalDateTime dateTime = parseLocalDateTime(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = getInt(request,"calories");
        Meal meal = new Meal(dateTime, description, calories);
        if (mealId.isEmpty()) {
            service.create(meal);
        } else {
            int id = Integer.parseInt(mealId);
            meal.setId(id);
            log.info("meal {}", meal);
            service.update(meal, id);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        action = action == null ? "all" : action;
        log.info("doGet action {}", action);
        switch (action){
            case "create":
            case "update":
                log.info("{}", action);
                Meal meal = action.equals("update") ? service.get(getInt(request, "id"))
                        : new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "delete":
                log.info("delete {}", action);
                service.delete(getInt(request, "id"));
                response.sendRedirect("meals");
                break;
            case "between":
                break;
            case "all":
            default:
                log.info("default {}", action);
                List<MealTo> mealsTo = filteredByStreams(service.getAll(), LocalDateTime.MIN.toLocalTime(), LocalDateTime.MAX.toLocalTime(), CALORIES_REP_DAY);
                request.setAttribute("meals", mealsTo);
                request.setAttribute("formatter", DATE_TIME_FORMATTER);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }
    private Integer getInt(HttpServletRequest request, String nameParam){
        return Integer.parseInt(request.getParameter(nameParam));
    }
}
