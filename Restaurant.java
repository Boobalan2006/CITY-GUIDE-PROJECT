import java.util.ArrayList;
import java.util.List;

class Restaurant {
    String name, location, cuisineType, priceRange;
    double rating;

    public Restaurant(String name, String location, String cuisineType, double rating, String priceRange) {
        this.name = name;
        this.location = location;
        this.cuisineType = cuisineType;
        this.rating = rating;
        this.priceRange = priceRange;
    }

    public String getLocation() { return location; }
    public String getCuisineType() { return cuisineType; }
    public double getRating() { return rating; }
    public String getPriceRange() { return priceRange; }

    @Override
    public String toString() {
        return "Restaurant: " + name + " | Location: " + location + " | Cuisine: " + cuisineType
                + " | Rating: " + rating + " | Price Range: " + priceRange;
    }
}

class CityGuide {
    private List<Restaurant> restaurants;

    public CityGuide() {
        restaurants = new ArrayList<>();
        addSampleRestaurants();
    }

    private void addSampleRestaurants() {
        restaurants.add(new Restaurant("Central Bistro", "Downtown", "Italian", 4.5, "$$"));
        restaurants.add(new Restaurant("Sapphire Grill", "Midtown", "Steakhouse", 4.8, "$$$"));
        restaurants.add(new Restaurant("Urban Eats", "Uptown", "Sushi", 4.3, "$$$"));
        restaurants.add(new Restaurant("Green Leaf Cafe", "Downtown", "Vegan", 4.0, "$$"));
        restaurants.add(new Restaurant("Sunset Diner", "Seaside", "American", 3.9, "$"));
        restaurants.add(new Restaurant("Blue Moon Cafe", "Old Town", "French", 4.7, "$$$"));
        restaurants.add(new Restaurant("Seaside Grill", "Seaside", "Seafood", 4.2, "$$"));
        restaurants.add(new Restaurant("Golden Dragon", "Chinatown", "Chinese", 4.3, "$"));
        restaurants.add(new Restaurant("La Fiesta", "Downtown", "Mexican", 4.1, "$"));
        restaurants.add(new Restaurant("Burger Barn", "Midtown", "American", 4.0, "$"));
    }
   public List<Restaurant> getRecommendations(String location, String cuisineType, String priceRange, double minRating) {
        List<Restaurant> recommendations = new ArrayList<>();
        for (Restaurant r : restaurants) {
            if (r.getLocation().equalsIgnoreCase(location)
                    && r.getCuisineType().equalsIgnoreCase(cuisineType)
                    && r.getPriceRange().equalsIgnoreCase(priceRange)
                    && r.getRating() >= minRating) {
                recommendations.add(r);
            }
        }
        return recommendations;
    }
}