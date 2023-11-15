package com.example.tp_final_sauce_algerienne_proj_2.model;

import android.content.res.Resources;

import com.google.android.gms.maps.model.Polyline;

import java.util.List;

public class DirectionsResponse {
    private List<Route> routes;
    private String status;

    public List<Route> getRoutes() {
        return routes;
    }

    public String getStatus() {
        return status;
    }

    public static class Route {
        private OverviewPolyline overview_polyline;
        private List<Leg> legs;

        public OverviewPolyline getOverviewPolyline() {
            return overview_polyline;
        }

        public List<Leg> getLegs() {
            return legs;
        }

        public static class OverviewPolyline {
            private String points;

            public String getPoints() {
                return points;
            }
        }

        public static class Leg {
            private Distance distance;
            private Duration duration;
            private String end_address;
            private String start_address;
            private List<Step> steps;

            public Distance getDistance() {
                return distance;
            }

            public Duration getDuration() {
                return duration;
            }

            public String getStartAddress() {
                return end_address;
            }

            public String getEndAddress() {
                return start_address;
            }

            public static class Step {
                private Distance distance;
                private Duration duration;
                private String html_instructions;
                private Polyline polyline;
                private String travel_mode;

                public static class Polyline {
                    private String points;

                    public String getPoints() {
                        return points;
                    }
                }
            }
        }

        public static class Distance {
            private String text;
            private long value;

            public long getValue() {
                return value;
            }
        }

        public static class Duration {
            private String text;
            private long value;

            public long getValue() {
                return value;
            }
        }
    }
}

