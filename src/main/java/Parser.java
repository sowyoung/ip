/** Classifies the command keyword entered by the user. */
public class Parser {
    /** The command categories understood by Tung Tung. */
    public enum CommandType { LIST, MARK, UNMARK, DELETE, ADD }

    /**
     * Identifies which operation a command requests.
     *
     * @param input complete command entered by the user
     * @return command category for the input
     */
    public CommandType identify(String input) {
        if (input.equals("list")) {
            return CommandType.LIST;
        }
        if (input.equals("mark") || input.startsWith("mark ")) {
            return CommandType.MARK;
        }
        if (input.equals("unmark") || input.startsWith("unmark ")) {
            return CommandType.UNMARK;
        }
        if (input.equals("delete") || input.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        return CommandType.ADD;
    }
}
