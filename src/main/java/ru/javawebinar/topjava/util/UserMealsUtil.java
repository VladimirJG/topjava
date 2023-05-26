package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 21, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 17, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 17, 13, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.MAY, 17, 20, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesInDayMap = getCaloriesInDay(meals);
        List<UserMealWithExcess> filteredListOfValues = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredListOfValues.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesInDayMap.get(date) > caloriesPerDay));
            }
        }
        return filteredListOfValues;
    }

    private static Map<LocalDate, Integer> getCaloriesInDay(List<UserMeal> meals) {
        Map<LocalDate, Integer> caloriesInDayMap = new HashMap<>();
        for (UserMeal meal : meals) {
            LocalDate key = meal.getDateTime().toLocalDate();
            int value = meal.getCalories();
            caloriesInDayMap.merge(key, value, Integer::sum);
        }
        return caloriesInDayMap;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesInDayMap = getCaloriesInDayThroughLambda(meals);
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(food -> new UserMealWithExcess(food.getDateTime(), food.getDescription(), food.getCalories(),
                        caloriesInDayMap.get(food.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }

    private static Map<LocalDate, Integer> getCaloriesInDayThroughLambda(List<UserMeal> meals) {
        return meals.stream()
                .collect(Collectors.groupingBy(food -> food.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
    }

    public static List<UserMealWithExcess> filteredByCycles2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        LocalDate dateNoTime = meals.get(0).getDateTime().toLocalDate();
        List<UserMealWithExcess> filteredListOfValues = new ArrayList<>();
        Map<LocalDate, Integer> caloriesInDayMap = new HashMap<>();
        for (UserMeal meal : meals) {
            if (meal.getDateTime().toLocalDate().equals(dateNoTime)) {
                caloriesInDayMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    filteredListOfValues.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                            false));
                }
                if (caloriesInDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    filteredListOfValues.get(filteredListOfValues.size() - 1).setExcess(true);
                }
            } else {
                dateNoTime = meal.getDateTime().toLocalDate();
                caloriesInDayMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    filteredListOfValues.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                            false));
                }
                if (caloriesInDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                    filteredListOfValues.get(filteredListOfValues.size() - 2).setExcess(true);
                }
            }
        }
        return filteredListOfValues;
    }
}