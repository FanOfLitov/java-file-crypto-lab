//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            if (args.length == 0){
                printHelp();
                return;
            }

            System.out.println("Command: " + args[0]);

        }

        private static void printHelp(){
        System.out.println("Java File Crypto");
        System.out.println();
        System.out.println("Usage:");
        System.out.println(" encryptor <input> <output> [password]");
        System.out.println(" decrypt <input> <output> <password>");
        }
    }
