package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;


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

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        Map<Integer, Integer> map = getCaloriesInDayThroughLambda(meals);
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        map.getOrDefault(meal.getDateTime().getDayOfMonth(), -1) > caloriesPerDay));
            }
        }
        return list;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> list = new ArrayList<>();
        meals.stream()
                .filter(s -> s.getDateTime().getHour() > startTime.getHour() && s.getDateTime().getHour() < endTime.getHour())
                .forEach(s -> list.add(new UserMealWithExcess(s.getDateTime(), s.getDescription(), s.getCalories(),
                        getCaloriesInDay(meals).getOrDefault(s.getDateTime().getDayOfMonth(), -1) > caloriesPerDay)));
        return list;
    }

    public static Map<Integer, Integer> getCaloriesInDay(List<UserMeal> meals) {
        Map<Integer, Integer> map = new HashMap<>();
        for (UserMeal meal : meals) {
            int key = meal.getDateTime().getDayOfMonth();
            int value = meal.getCalories();
            map.merge(key, map.getOrDefault(key, value), (mapKey, mapValue) -> mapValue + value);
        }
        return map;
    }

    public static Map<Integer, Integer> getCaloriesInDayThroughLambda(List<UserMeal> meals) {
        Map<Integer, Integer> map = new HashMap<>();
        meals.stream().map(s -> map.merge(s.getDateTime().getDayOfMonth(),
                map.getOrDefault(s.getDateTime().getDayOfMonth(), s.getCalories()),
                (a, b) -> b + s.getCalories())).collect(Collectors.toList());
        return map;
    }
}
