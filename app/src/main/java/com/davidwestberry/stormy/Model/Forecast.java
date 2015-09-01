package com.davidwestberry.stormy.Model;

import java.util.ArrayList;

/**
 * Created by DavidW on 7/27/2015.
 */
public class Forecast {

    public ArrayList<Weather> weatherArray = new ArrayList<Weather>();
    public City city = new City();

    public class City{
        private String name;
        private String country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
