package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );


        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        meals.sort(Comparator.comparing(UserMeal::getDateTime));
        List<UserMealWithExcess> list = new ArrayList<>();
        List<Integer> arrayOfCalories = new ArrayList<>();
        int day = meals.get(0).getDateTime().getDayOfMonth();
        int calories = 0;
        for (UserMeal meal : meals) {
            if (meal.getDateTime().getDayOfMonth() != day) {
                arrayOfCalories.add(calories);
                day = meal.getDateTime().getDayOfMonth();
                calories = 0;
            }
            calories += meal.getCalories();
        }
        arrayOfCalories.add(calories);
        for (int i = 0, j = 0; i < meals.size(); i++) {
            if (TimeUtil.isBetweenHalfOpen(meals.get(i).getDateTime().toLocalTime(), startTime, endTime)) {
                boolean excess;
                excess = arrayOfCalories.get(j) > caloriesPerDay;
                j++;
                list.add(new UserMealWithExcess(meals.get(i).getDateTime(), meals.get(i).getDescription(), meals.get(i).getCalories(), excess));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
