public class Newton {


    public static void main(String[] args) {
        // Read in values
        if (args.length == 3 {
            sqRoot(Double.parseDouble(args[0]),
                   Double.parseDouble(args[1]),
                   Double.parseDouble(args[2]));
            sqRoot(Double.parseDouble(args[0]),
                   Double.parseDouble(args[1]));


        } else if (args.length == 2) {
            sqRoot(Double.parseDouble(args[0]),
                   Double.parseDouble(args[1]));
        } else {
            System.out.println("Incorrect Number of Parameters\nUsage: java Newton number guess epsilon");
        }
    }


    public static void sqRoot(double n, double guess) {
        double epsilon = 0.0000001;
        double newGuess = ((n / guess) + guess) / 2;
        int i = 1;

        if (Math.abs(guess - newGuess) < epsilon) {
            System.out.println(i);
            System.out.println(newGuess);
            return;
        }

        else {
            i += 1;
        }

        while (Math.abs(guess - newGuess) > epsilon) {
            i += 1;
            guess = newGuess;
            newGuess = ((n / guess) + guess) / 2;
            System.out.println(newGuess);
        }

        System.out.println(newGuess);
    }


    public static void sqRoot(double n, double guess, double epsilon) {
        double newGuess = ((n / guess) + guess) / 2;
        int i = 1;

        if (Math.abs(guess - newGuess) < epsilon) {
            System.out.println(i);
            System.out.println(newGuess);
            return;
        }

        else {
            i += 1;
        }

        while (Math.abs(guess - newGuess) > epsilon) {
            i += 1;
            guess = newGuess;
            newGuess = ((n / guess) + guess) / 2;
        }

        System.out.println(newGuess);
    }
}
