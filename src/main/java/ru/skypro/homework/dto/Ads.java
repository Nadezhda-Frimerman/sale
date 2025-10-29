package ru.skypro.homework.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Ads {
    private Integer count = 0;
    private Collection<Ad> results = new ArrayList<>();

    public Ads() {
    }

    public Ads(Integer count, Collection<Ad> results) {
        this.count = count;
        this.results = results;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Collection<Ad> getResults() {
        return results;
    }

    public void setResults(List<Ad> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return Objects.equals(count, ads.count) && Objects.equals(results, ads.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, results);
    }

    @Override
    public String toString() {
        return "Ads{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}
