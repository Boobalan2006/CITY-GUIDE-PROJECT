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
public class CityGuideUI {
    private JFrame frame;
    private JComboBox<String> locationBox, cuisineBox, priceBox;
    private JTextField ratingField;
    private JTextArea resultArea;
    private JButton searchButton;
    private CityGuide guide;

    public CityGuideUI() {
        guide = new CityGuide();
        createUI();
    }

    private void createUI() {
        frame = new JFrame("City Guide Application");
        frame.setSize(800, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 204, 153),
                                                     0, getHeight(), new Color(102, 178, 255));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new FlowLayout());
        frame.add(mainPanel, BorderLayout.CENTER);

        // Dropdown options
        String[] locations = {"Downtown", "Midtown", "Uptown", "Seaside", "Old Town", "Chinatown"};
        String[] cuisines = {"Italian", "Steakhouse", "Sushi", "Vegan", "American", "French", "Seafood", "Chinese", "Mexican"};
        String[] priceRanges = {"$", "$$", "$$$"};
         locationBox = new JComboBox<>(locations);
        cuisineBox = new JComboBox<>(cuisines);
        priceBox = new JComboBox<>(priceRanges);

        ratingField = new JTextField(5);

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(51, 153, 255));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);

        // Hover animation
        searchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                searchButton.setBackground(new Color(30, 144, 255));
            }

            public void mouseExited(MouseEvent e) {
                searchButton.setBackground(new Color(51, 153, 255));
            }
        });

        resultArea = new JTextArea(10, 65);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Verdana", Font.PLAIN, 14));
        resultArea.setBackground(new Color(255, 255, 230));
 // Add components
        mainPanel.add(new JLabel("Select Location:"));
        mainPanel.add(locationBox);
        mainPanel.add(new JLabel("Select Cuisine:"));
        mainPanel.add(cuisineBox);
        mainPanel.add(new JLabel("Select Price Range:"));
        mainPanel.add(priceBox);
        mainPanel.add(new JLabel("Enter Minimum Rating (0.0 - 5.0):"));
        mainPanel.add(ratingField);
        mainPanel.add(searchButton);
        mainPanel.add(new JScrollPane(resultArea));

        // Button Action
        searchButton.addActionListener(e -> animateButtonClick());

        frame.setVisible(true);
    }
      private void animateButtonClick() {
        // Bounce animation for Search button
        Point p = searchButton.getLocation();
        Timer timer = new Timer(15, new ActionListener() {
            int step = 0;
            boolean down = true;

            public void actionPerformed(ActionEvent e) {
                if (down) {
                    searchButton.setLocation(p.x, p.y + step);
                    step++;
                    if (step > 5) down = false;
                } else {
                    searchButton.setLocation(p.x, p.y + step);
                    step--;
                    if (step == 0) {
                        ((Timer) e.getSource()).stop();
                        searchRestaurants(); // Perform search after bounce
                    }
                }
            }
        });
        timer.start();
    }

    private void searchRestaurants() {
        String location = locationBox.getSelectedItem().toString();
        String cuisine = cuisineBox.getSelectedItem().toString();
        String price = priceBox.getSelectedItem().toString();
        double rating;

        try {
            rating = Double.parseDouble(ratingField.getText());
        } catch (NumberFormatException ex) {
            animateText("Please enter a valid rating (0.0 to 5.0)");
            return;
        }

        List<Restaurant> results = guide.getRecommendations(location, cuisine, price, rating);

        if (results.isEmpty()) {
            animateText("No restaurants found for the given criteria.");
        } else {
            animateText("Recommended Restaurants:\n");
            for (Restaurant r : results) {
                animateText(r.toString() + "\n");
            }
        }
    }
 private void animateText(String text) {
        new Thread(() -> {
            resultArea.append("\n");
            for (char c : text.toCharArray()) {
                resultArea.append(String.valueOf(c));
                try {
                    Thread.sleep(15); // animation speed
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CityGuideUI::new);
    }
}