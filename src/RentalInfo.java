import java.util.HashMap;

public class RentalInfo {
  private static final String REGULAR_CODE = "regular";
  private HashMap<String, Movie> movies;
  public RentalInfo() {
    movies = new HashMap<>();
    movies.put("F001", new Movie("You've Got Mail", REGULAR_CODE));
    movies.put("F002", new Movie("Matrix", REGULAR_CODE));
    movies.put("F003", new Movie("Cars", "childrens"));
    movies.put("F004", new Movie("Fast & Furious X", "new"));
  }

  public String statement(Customer customer) {
    double totalAmount = 0;
    int frequentEnterPoints = 0;
    String result = "Rental Record for " + customer.getName() + "\n";

    for (MovieRental rental : customer.getRentals()) {
      double thisAmount = calculateAmount(rental);

      frequentEnterPoints += calculateFrequentEnterPoints(rental);

      result += "\t" + movies.get(rental.getMovieId()).getTitle() + "\t" + thisAmount + "\n";
      totalAmount += thisAmount;
    }

    result += "Amount owed is " + totalAmount + "\n";
    result += "You earned " + frequentEnterPoints + " frequent points\n";

    return result;
  }

  private double calculateAmount(MovieRental rental) {
    double amount = 0;
    Movie movie = movies.get(rental.getMovieId());
    String code = movie.getCode();

    if (code.equals(REGULAR_CODE)) {
      amount = 2;
      if (rental.getDays() > 2) {
        amount += (rental.getDays() - 2) * 1.5;
      }
    } else if (code.equals("new")) {
      amount = rental.getDays() * 3d;
    } else if (code.equals("childrens")) {
      amount = 1.5;
      if (rental.getDays() > 3) {
        amount += (rental.getDays() - 3) * 1.5;
      }
    }

    return amount;
  }

  private int calculateFrequentEnterPoints(MovieRental rental) {
    int points = 1;
    Movie movie = movies.get(rental.getMovieId());
    String code = movie.getCode();

    if (code.equals("new") && rental.getDays() > 2) {
      points++;
    }

    return points;
  }
}
