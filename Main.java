import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.Timer;

class IntervalCount {
    private int i = 0;
    private int j = 61;
    private int coinCount = 91;

    public int getCount() {
        return i;
    }

    public int getObstacleCount() {
        return j;
    }

    public void setCount(int count) {
        this.i = count;
    }

    public void setObstacleCount(int count) {
        this.j = count;
    }

    public void incrementObstacleCount(int i) {
        this.j += i;
    }

    public void increment(int i) {
        this.i += i;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public void incrementCoinCount(int i) {
        this.coinCount += i;
    }
}

class TextAreaDialog {
    private JDialog dialog;
    private JTextArea textArea;

    private static Line lines;

    public TextAreaDialog(Frame parent) {
        // Initialize the text area
        textArea = new JTextArea(10, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Set a monospaced font
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Create a scroll pane to make sure text area scrolls if needed
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Create the dialog
        dialog = new JDialog(parent, "Text Area Dialog", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Create and add a button to close the dialog
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> {
            dialog.dispose();
            System.exit(0);
        });
        dialog.add(closeButton, BorderLayout.SOUTH);

        // Set the size and location of the dialog
        dialog.setSize(400, 330);
        dialog.setLocationRelativeTo(parent);
        dialog.requestFocusInWindow();

        // Add a KeyListener to the panel
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               System.out.println(e.getKeyCode());
                // Check if the pressed key is the space bar
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (lines.getLine1().indexOf(Line.character) == 5) {
                        lines.jump(lines);
                    }
                } else if (Line.lost && e.getKeyCode() == 82) {
                    restartApplication();
                } else if (e.getKeyCode() > 64 && e.getKeyCode() < 91) {
                    Line.character2 = Line.character;
                    Line.character = e.getKeyChar();
                }
            }
        });

        // Make the panel focusable and request focus
        textArea.setFocusable(true);
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public static void setLine(Line l) {
        lines = l;
    }

    public String getText() {
        return textArea.getText();
    }

    public void showDialog() {
        dialog.setVisible(true);
    }

    private static void restartApplication() {
        try {
            // Get the runtime object
            Runtime runtime = Runtime.getRuntime();

            // Get the current Java executable
            String javaExecutable = System.getProperty("java.home") + "/bin/java";

            // Get the current classpath
            String classpath = System.getProperty("java.class.path");

            // Construct the command to restart the application
            String[] command = new String[]{
                    javaExecutable,
                    "-cp", classpath,
                    Main.class.getName()
            };

            // Execute the command to restart
            runtime.exec(command);

            // Exit the current JVM
            System.exit(0);
        } catch (Exception e) {

        }

    }
}
    class Line {
        public static char character = 'i';
        public static char character2 = character;

        private String line1 = "*\s\s\s\s"+character2+"\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        private String line2 = "*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        private String line3 = "*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        private String line4 = "*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";


        private static int obstacleFrequency = 80; // in milliseconds

        private static int score = 0;
        private static int level = 1;
        private static String lineScore = "*\s\sScore:\s"+score+"\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        private static String lineLevel = "*\s\sLevel:\s"+level+"\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";

        private static String message = "*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\sSpacebar to Jump!\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        private static String message2 = "*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";

        private static int speed = 12;
        private static int coinFrequency = 12;

        private static String direction = "up"; // used for jumping
        private static int floatCount = 0; // we need this to let the i float a bit in the air while at the top of the jump

        public static boolean lost = false;

        public String getLine1() {
            return line1;
        }

        public void setLine1(String line1) {
            this.line1 = line1;
        }

        public String getLine2() {
            return line2;
        }

        public void setLine2(String line2) {
            this.line2 = line2;
        }

        public String getLine3() {
            return line3;
        }

        public void setLine3(String line3) {
            this.line3 = line3;
        }

        public String getLine4() {
            return line4;
        }

        public void setLine4(String line4) {
            this.line4 = line4;
        }

        public static int getScore() {
            return score;
        }

        public static void increaseScore(int s) {
            score += s;
            lineScore = "*\s\sScore:\s"+score+"\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        }

        public static String getLineScore() {
            return lineScore;
        }

        public static String getMessage() {
            return message;
        }

        public static String getMessage2() {
            return message2;
        }

        public static void setMessage(String m) {
            message = m;

        }

        public static void setMessage2(String m) {
            message2 = m;

        }

        public static String getLineLevel(){
            return lineLevel;
        }

        public static void setLevel(int l){
            level = l;
            lineLevel = "*\s\sLevel:\s"+level+"\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*";
        }

        public static int getSpeed(){
            return speed;
        }

        public static void setSpeed(int s) {
            speed = s;
        }

        public static int getCoinFrequency(){
            return coinFrequency;
        }

        public static void setCoinFrequency(int s) {
            coinFrequency = s;
        }

        public static int getObstacleFrequency() {
            return obstacleFrequency;
        }

        public static void setObstacleFrequency(int f) {
            obstacleFrequency = f;
        }

        public void jump(Line lines){
            int intervalMillis = 100;

            Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    StringBuilder l1 = new StringBuilder(lines.getLine1());
                    StringBuilder l2 = new StringBuilder(lines.getLine2());
                    StringBuilder l3 = new StringBuilder(lines.getLine3());
                    StringBuilder l4 = new StringBuilder(lines.getLine4());

                    if (direction.equals("up")) {
                        if (lines.getLine1().indexOf(Line.character) == 5) {
                            l1.deleteCharAt(5);
                            l1.insert(5, '\s');

                            l2.deleteCharAt(5);
                            l2.insert(5, Line.character);

                            lines.setLine1(l1.toString());
                            lines.setLine2(l2.toString());

                        } else if (lines.getLine2().indexOf(Line.character) == 5){
                            l2.deleteCharAt(5);
                            l2.insert(5, '\s');

                            l3.deleteCharAt(5);
                            l3.insert(5, Line.character);

                            lines.setLine2(l2.toString());
                            lines.setLine3(l3.toString());
                        } else if (lines.getLine3().indexOf(Line.character) == 5){
                            l3.deleteCharAt(5);
                            l3.insert(5, '\s');

                            l4.deleteCharAt(5);
                            l4.insert(5, Line.character);

                            lines.setLine3(l3.toString());
                            lines.setLine4(l4.toString());

                        }
                        else if (floatCount == 2)
                            direction = "down";
                        else if (lines.getLine4().indexOf(Line.character) == 5)
                            floatCount++;
                    } else {
                        if (lines.getLine4().indexOf(Line.character) == 5) {

                            l4.deleteCharAt(5);
                            l4.insert(5, '\s');

                            l3.deleteCharAt(5);
                            l3.insert(5, Line.character);

                            lines.setLine4(l4.toString());
                            lines.setLine3(l3.toString());

                        } else if (lines.getLine3().indexOf(Line.character) == 5){
                            l3.deleteCharAt(5);
                            l3.insert(5, '\s');

                            l2.deleteCharAt(5);
                            l2.insert(5, Line.character);

                            lines.setLine3(l3.toString());
                            lines.setLine2(l2.toString());

                        } else if (lines.getLine2().indexOf(Line.character) == 5){
                            l2.deleteCharAt(5);
                            l2.insert(5, '\s');

                            l1.deleteCharAt(5);
                            l1.insert(5, Line.character);

                            lines.setLine2(l2.toString());
                            lines.setLine1(l1.toString());

                            direction = "up";
                            floatCount = 0;
                            Line.increaseScore(1);
                            timer.cancel();
                        }
                    }
                }
            };


            timer.scheduleAtFixedRate(task, 0, intervalMillis);
        }
    }

    public class Main {
        public static void main(String[] args) {

            Line lines = new Line();
            TextAreaDialog.setLine(lines);

            int intervalMillis = 1;

            IntervalCount count = new IntervalCount();

            Timer timer = new Timer();

            TextAreaDialog textAreaDialog = new TextAreaDialog(null);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {

                    updateCharacter(lines);

                    // Set some initial text (optional)
                    textAreaDialog.setText(displayMenu(lines.getLine1(), lines.getLine2(), lines.getLine3(), lines.getLine4(), Line.getLineScore()));

                    count.increment(1);

                    if (Line.getScore() > 45) {
                        Line.setObstacleFrequency(50);
                        Line.setLevel(4);
                    }
                    else if (Line.getScore() > 30){
                        Line.setObstacleFrequency(60);
                        Line.setLevel(3);
                    }
                    else if (Line.getScore() > 15){
                        Line.setObstacleFrequency(70);
                        Line.setLevel(2);
                    }

                    if (count.getCount() > Line.getObstacleFrequency()) {
                        lines.setLine1(animateObstacles("right", lines.getLine1(), timer));
                        lines.setLine2(animateObstacles("right", lines.getLine2(), timer));
                        lines.setLine3(animateObstacles("right", lines.getLine3(), timer));
                        lines.setLine4(animateObstacles("right", lines.getLine4(), timer));

                        count.incrementObstacleCount(1); // increment Obstacle timer
                        count.incrementCoinCount(1); // increment Coin timer
                        count.setCount(0); // reset animation timer



                        if(count.getObstacleCount() > Line.getSpeed()) {
                            // Create an instance of Random
                            Random random = new Random();

                            // Generate a random integer between 0 (inclusive) and 4 (exclusive)
                            int randomNumber = random.nextInt(3);

                            //generate an obstacle the size of the randomNumber
                            switch (randomNumber) {
                                case 0: lines.setLine1(insertObstacle(lines.getLine1())); break;
                                case 1:
                                    lines.setLine1(insertObstacle(lines.getLine1()));
                                    lines.setLine2(insertObstacle(lines.getLine2())); break;
                                case 2:
                                    lines.setLine1(insertObstacle(lines.getLine1()));
                                    lines.setLine2(insertObstacle(lines.getLine2()));
                                    lines.setLine3(insertObstacle(lines.getLine3())); break;
                            }
                            count.setObstacleCount(0);
                            int randomSpeed = random.nextInt(27);
                            randomSpeed--;
                            Line.setSpeed(randomSpeed + 12);
                        }

                        if(count.getCoinCount() > Line.getSpeed()) {
                            // Create an instance of Random
                            Random random = new Random();

                            // Generate a random integer between 0 (inclusive) and 5 (exclusive)
                            int randomNumber = random.nextInt(4);

                            //generate an obstacle the size of the randomNumber
                            switch (randomNumber) {
                                case 0: lines.setLine1(insertCoin(lines.getLine1())); break;
                                case 1:
                                    lines.setLine2(insertCoin(lines.getLine2())); break;
                                case 2:
                                    lines.setLine3(insertCoin(lines.getLine3())); break;
                                case 3:
                                    lines.setLine4(insertCoin(lines.getLine4())); break;
                            }
                            count.setCoinCount(0);
                        }

                    }
                    textAreaDialog.setText(displayMenu(lines.getLine1(), lines.getLine2(), lines.getLine3(), lines.getLine4(), Line.getLineScore()));
                }
            };

            timer.scheduleAtFixedRate(task, 0, intervalMillis);

            // Show the dialog
            textAreaDialog.showDialog();

        }

        public static void updateCharacter(Line lines){
            StringBuilder l1 = new StringBuilder(lines.getLine1());
            StringBuilder l2 = new StringBuilder(lines.getLine2());
            StringBuilder l3 = new StringBuilder(lines.getLine3());
            StringBuilder l4 = new StringBuilder(lines.getLine4());

            if (l1.charAt(5) == Line.character2 && Line.character != Line.character2) {
                l1.deleteCharAt(5);
                l1.insert(5, Line.character);
                lines.setLine1(l1.toString());
            }
            else if (l2.charAt(5) == Line.character2 && Line.character != Line.character2) {
                l2.deleteCharAt(5);
                l2.insert(5, Line.character);
                lines.setLine2(l2.toString());
            }
            else if (l3.charAt(5) == Line.character2 && Line.character != Line.character2) {
                l3.deleteCharAt(5);
                l3.insert(5, Line.character);
                lines.setLine3(l3.toString());
            }
            else if (l4.charAt(5) == Line.character2 && Line.character != Line.character2) {
                l4.deleteCharAt(5);
                l4.insert(5, Line.character);
                lines.setLine4(l4.toString());
            }

        }

        public static String displayMenu(String l1, String l2, String l3, String l4, String lScore) {
            return ("""
                ****************************************************
                *                                                  *
                """
                    +lScore+"\n"+
                    Line.getLineLevel()+
                    """
                    
                    *                                                  *
                    *                                                  *
                    *                                                  *
                    """
                    +Line.getMessage()+"\n"+
                    Line.getMessage2()+
                    """
                    
                    *                                                  *
                    *                                                  *
                    *                                                  *
                    *                                                  *
                    """
                    +l4+"\n"+
                    l3+"\n"+
                    l2+"\n"+
                    l1+
                    """
                     
                     ****************************************************
                     
                     """);
        }

        public static String animateObstacles (String direction, String line, Timer timer) {

            // Find all star indices
            List<Integer> starIndices = new ArrayList<>();
            int index = line.indexOf('*');
            while (index != -1) {
                starIndices.add(index);
                index = line.indexOf('*', index + 1);
            }

            List<Integer> coinIndices = new ArrayList<>();
            int coinIndex = line.indexOf('0');
            while (coinIndex != -1) {
                coinIndices.add(coinIndex);
                coinIndex = line.indexOf('0', coinIndex + 1);
            }

            // Check if we have enough stars to work with
            if (starIndices.size() < 3 && coinIndices.isEmpty()) {
                return line; // Not enough stars to shift middle ones
            }

            // Process all middle stars
            StringBuilder sb = new StringBuilder(line);
            for (int i = 1; i < starIndices.size() - 1; i++) {
                int currentStarIndex = starIndices.get(i);
                int newStarIndex = (direction.equals("left")) ? currentStarIndex + 1 : currentStarIndex - 1;

                // Ensure newStarIndex is within bounds and not overwriting another star
                if(sb.charAt(newStarIndex) == Line.character){
                    timer.cancel();
                    Line.setMessage("*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\sYou Lose!\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*");
                    Line.setMessage2("*\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\sPress R to restart\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s*");
                    Line.lost = true;

                } else if (newStarIndex >= 0 && newStarIndex < sb.length() && sb.charAt(newStarIndex) != '*') {
                    // Remove the old star
                    sb.deleteCharAt(currentStarIndex);

                    // Insert the star at the new position
                    if (newStarIndex == 1)
                        sb.insert(newStarIndex, '\s');
                    else
                        sb.insert(newStarIndex, '*');
                    //System.out.println(newStarIndex);
                }
            }

            //System.out.print(coinIndices.size());
            // Process all coins
            for (int i = coinIndices.size() - 1; i >= 0; i--) {
                int currentCoinIndex = coinIndices.get(i);
                int newCoinIndex = (direction.equals("left")) ? currentCoinIndex + 1 : currentCoinIndex - 1;

                if (newCoinIndex >= 0 && newCoinIndex < sb.length()) {
                    if (sb.charAt(newCoinIndex) == Line.character) {
                        // Example action: increase score
                        Line.increaseScore(1);
                        sb.deleteCharAt(currentCoinIndex);
                        sb.insert(currentCoinIndex, "\s");
                    } else if (sb.charAt(newCoinIndex) != '*') {
                        // Remove the old coin
                        sb.deleteCharAt(currentCoinIndex);

                        // Insert the coin at the new position
                        if (newCoinIndex == 1)
                            sb.insert(newCoinIndex, ' ');
                        else
                            sb.insert(newCoinIndex, '0');
                    }
                }
            }

            return sb.toString();
        }

        public static String insertObstacle (String line) {

            StringBuilder sb = new StringBuilder(line);

            sb.deleteCharAt(50);
            sb.insert(49, '*');

            return sb.toString();
        }

        public static String insertCoin (String line) {
            StringBuilder sb = new StringBuilder(line);

            if(sb.charAt(50) != '*'){
                sb.deleteCharAt(50);
                sb.insert(49, '0');
            }

            return sb.toString();
        }

    }