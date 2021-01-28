public class stringFix {
    public static String spaceFix(String string)
    {
        String returnValue = string.trim();
        returnValue = returnValue.replace(' ','_');
        return returnValue;
    }
}
