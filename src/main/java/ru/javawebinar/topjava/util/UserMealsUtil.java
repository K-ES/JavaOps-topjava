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
        List<UserMeal> meals = Arrays.asList(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));

        List<UserMealWithExcess> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);


    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        System.out.println("проверочка");
        System.out.println("Длина списка" + meals.size());
        System.out.println("Начальная дата и время " + startTime);
        System.out.println("Конечные дата и время " + startTime);
        List<UserMealWithExcess> resultList = new ArrayList<>();

        // создаем мапу для подсчета калорий
        Map<LocalDate, Integer> mapDateCalories = new HashMap<>();
        // тест сегодняшнего коммита
        for (UserMeal userMeal : meals) {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();

            // если значения нет, пихаем его, если оно есть, то прибавляем
            if (!mapDateCalories.containsKey(localDate)) {
                mapDateCalories.put(localDate, userMeal.getCalories());
            } else {
                mapDateCalories.put(localDate, userMeal.getCalories() + mapDateCalories.get(localDate));
            }
        }
        System.out.println("Сформировали мапу");
        for (LocalDate currentDate : mapDateCalories.keySet()) {
            System.out.println(currentDate + " " + mapDateCalories.get(currentDate));
        }

        // бежим второй раз по списку и это значит, что мы не повышаем сложность
        for (UserMeal userMeal : meals) {
            System.out.println("Теукщий userMeal " + userMeal.toString());
            LocalTime localTime = LocalTime.of(userMeal.getDateTime().getHour(), userMeal.getDateTime().getMinute(), userMeal.getDateTime().getSecond());
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            System.out.println("Полученное время: " + localTime);
            if ((localTime.isAfter(startTime) && localTime.isBefore(endTime) || localTime.equals(startTime))) {
                UserMealWithExcess userMealWithExcess = new UserMealWithExcess(userMeal, mapDateCalories.get(localDate) > caloriesPerDay);
                resultList.add(userMealWithExcess);
            }

        }
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(
            List<UserMeal> meals, LocalTime
            startTime, LocalTime endTime, int caloriesPerDay
                                                            ) {
        System.out.println("Поехали стримачительно");
        // для начала надо создать группировку по датам и запихать ее в мапу


        Map<LocalDateTime, Long> tmpMap = meals.stream().collect(Collectors.groupingBy(UserMeal::getDateTime, Collectors.counting()));

        for (Map.Entry<LocalDateTime, Long> entry : tmpMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        
        return null;
    }
}




































