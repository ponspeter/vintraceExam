package com.vintrace.exam.test;

import com.vintrace.exam.component.GrapeComponent;
import com.vintrace.exam.model.Wine;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WineTest {

    public static void main(String[] args) {

        Wine w = Wine.builder()
                .lotCode("11YVCHAR001")
                .volume(1000)
                .description("2011 Yarra Valley Chardonnay")
                .tankCode("T25-01")
                .productState("Ready for bottling")
                .ownerName("YV Wines Pty Ltd")
                .components(Stream.of(
                        GrapeComponent.builder().percentage(80D).year(2011).variety("Chardonnay").region("Yarra Valley").build(),
                        GrapeComponent.builder().percentage(10D).year(2010).variety("Chardonnay").region("Macedon").build(),
                        GrapeComponent.builder().percentage(5D).year(2011).variety("Pinot Noir").region("Mornington").build(),
                        GrapeComponent.builder().percentage(5D).year(2010).variety("Pinot Noir").region("Macedon").build()).collect(Collectors.toSet()))
                .build();

        printYearBreakdown(w);
        printVarietyBreakdown(w);
        printRegionBreakdown(w);
        printYearAndVarietyBreakdown(w);

    }

    private static void printVarietyBreakdown(Wine w) {
        System.out.println("*** printVarietyBreakdown start *** ");
        Map<String, Double> map = w.getComponents()
                .stream()
                .collect(Collectors.groupingBy(GrapeComponent::getVariety,
                        Collectors.summingDouble(GrapeComponent::getPercentage)));

        map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(item->System.out.println(item.getValue() + "% - " + item.getKey()));
        System.out.println("*** printVarietyBreakdown end *** ");
    }

    private static void printYearBreakdown(Wine w) {
        System.out.println("*** printYearBreakdown start *** ");
        Map<Integer, Double> map = w.getComponents()
                .stream()
                .collect(Collectors.groupingBy(GrapeComponent::getYear,
                        Collectors.summingDouble(GrapeComponent::getPercentage)));

        map.entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .forEach(item->System.out.println(item.getValue() + "% - " + item.getKey()));

        System.out.println("*** printYearBreakdown end *** ");
    }

    private static void printRegionBreakdown(Wine w) {
        System.out.println("*** printRegionBreakdown start *** ");
        Map<String, Double> map = w.getComponents()
                .stream()
                .collect(Collectors.groupingBy(GrapeComponent::getRegion,
                        Collectors.summingDouble(GrapeComponent::getPercentage)));

        map.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(item->System.out.println(item.getValue() + "% - " + item.getKey()));
        System.out.println("*** printRegionBreakdown end *** ");
    }

    private static void printYearAndVarietyBreakdown(Wine w) {
        System.out.println("*** printYearAndVarietyBreakdown start *** ");
        Map<Integer, Map<String,Double>> map = w.getComponents()
                .stream()
                .collect(Collectors.groupingBy(GrapeComponent::getYear,
                        Collectors.groupingBy(GrapeComponent::getVariety,
                                Collectors.summingDouble(GrapeComponent::getPercentage))));

        System.out.println(map);

        Map<String, Double> res = map.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> String.valueOf(e.getKey()) +' '+ e.getValue().keySet(),
                        e -> e.getValue()
                                .entrySet()
                                .stream()
                                .collect(Collectors.summingDouble(value -> value.getValue()))
                ));

        res.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(item->System.out.println(
                        item.getValue() + "% - " + item.getKey()));

        System.out.println("*** printYearAndVarietyBreakdown end *** ");
    }

}

