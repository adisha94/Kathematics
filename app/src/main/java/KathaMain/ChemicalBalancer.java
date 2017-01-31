package KathaMain;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class ChemicalBalancer
{
    public static String balanceEquation(String input)
    {
        String[] tokens = input.split("\\s+");
        HashMap<String, Integer> elements = new HashMap<>(); //Amount is the column (vertical for matrix)
        HashMap<String, Integer> elementLeft = new HashMap<>();
        HashMap<String, Integer> elementRight = new HashMap<>();
        LinkedList<HashMap<String, Integer>> terms = new LinkedList<>(); //This stores the count of each. is the row for the matrix.
        boolean leftSide = true;
        boolean hasEqualSign = false;
        for (int i = 0; i < tokens.length; i++)
        {
            char ch = tokens[i].charAt(0); //Verify it is not a symbol.
            String term = tokens[i];
            if (ch == '=' || term.compareTo("->") == 0)
            {
                leftSide = false;
                hasEqualSign = true;
            }
            //Switching over to left side now.
            if (Character.isDigit(ch) || Character.isLetter(ch))
            {
                HashMap<String, Integer> aTerm = new HashMap<String, Integer>();
                terms.add(aTerm);
                int currentTerm = terms.size() - 1;
                int j = 0;
                while (j < term.length())
                {
                    char first = term.charAt(j);
                    if (Character.isDigit(first))
                    {
                        j = j + 1;
                    }
                    else
                    {
                        //Na2O
                        if (Character.isLowerCase(first))
                        {
                            System.out.println("Invalid chemical format, make sure you capitalize the letter.");
                            j = j + 1;
                        }
                        else
                        {
                            StringBuilder sb = new StringBuilder();
                            sb.append(first);
                            int subscript = 1;
                            if (j + 1 < term.length())
                            {
                                char second = term.charAt(j + 1);
                                if (Character.isLowerCase(second) && !Character.isDigit(second))
                                {
                                    sb.append(second);
                                    j = j + 1;
                                }
                                String rest = term.substring(j + 1, term.length());
                                int subscriptCounter = 0;
                                while (subscriptCounter < rest.length())
                                {
                                    if (Character.isDigit(rest.charAt(subscriptCounter)))
                                    {
                                        subscriptCounter++;
                                        j = j + 1;
                                    } else {
                                        break;
                                    }
                                }

                                if (rest.substring(0, subscriptCounter).compareTo("") != 0) {
                                    subscript = Integer.parseInt(rest.substring(0, subscriptCounter));
                                }
                            }
                            j = j + 1;
                            String element = sb.toString();
                            if (leftSide)
                            {
                                elementLeft.put(element, 1);
                            }
                            else
                            {
                                elementRight.put(element, 1);
                            }
                            elements.put(element, 1);
                            if (terms.get(currentTerm).get(element) != null) {
                                terms.get(currentTerm).put(element, terms.get(currentTerm).get(element) + subscript);
                            } else {
                                terms.get(currentTerm).put(element, subscript);
                            }

                        }

                    }
                }
            }
        }
        if (!hasEqualSign)
        {
            StringBuilder bz = new StringBuilder();
            for (int i = 0; i < tokens.length; i++)
            {
                bz.append(tokens[i] + " ");
            }
            bz.append("= ");
            for (String element : elements.keySet())
            {
                int count = 0;
                for (int i = 0; i < terms.size(); i++)
                {
                    if (terms.get(i).get(element) != null)
                        count += terms.get(i).get(element);
                }
                bz.append(element);
                if (count > 1)
                    bz.append(Integer.toString(count));
            }
            return bz.toString();
        }
        if (elementLeft.size() != elementRight.size())
        {
            System.out.println("Balance Error: Equation must have both elements on left and right");
        }
        int[][] mm = new int[terms.size()][elements.size()];
        for (int i = 0; i < terms.size(); i++)
        {
            int j = 0;
            for (String element : elements.keySet())
            {

                if (terms.get(i).get(element) == null)
                {
                    mm[i][j] = 0;
                }
                else
                {
                    mm[i][j] = terms.get(i).get(element);
                }
                j++;
            }
        }
        //Inefficient, I know.. but I'm fucking TIRED OF MATRIXES
        int[][] ro = new int[elements.size()][terms.size()];
        Iterator<String> it = elements.keySet().iterator();
        for (int i = 0; i < elements.size(); i++)
        {
            String ele = it.next();
            for (int j = 0; j < terms.size(); j++)
            {
                if (terms.get(j).get(ele) == null)
                {
                    ro[i][j] = 0;
                }
                else
                {
                    ro[i][j] = terms.get(j).get(ele);
                }
            }
        }
        //Matrix Library from Nayuki.
        Matrix<Fraction> mat = new Matrix<Fraction>(
                ro.length, ro[0].length, RationalField.FIELD);

        for (int i = 0; i < ro.length; i++)
        {
            for (int j = 0; j < ro[0].length; j++)
            {
                mat.set(i, j, new Fraction(ro[i][j], 1));
            }

        }
        mat.reducedRowEchelonForm();
        boolean unableToBalance = false;
        LinkedList<Integer> balancedValues = new LinkedList<>();

        int[] lcm = new int[terms.size() - 1];
        for (int i = 0; i < terms.size() - 1; i++)
        {
            Fraction frac = mat.get(i, mat.columnCount() - 1);
            lcm[i] = frac.denominator.intValue();
        }

        int lcmDenominator = lcmofarray(lcm, 0, lcm.length);

        for (int i = 0; i < terms.size(); i++)
        {
            if (i == terms.size() - 1)
            {
                balancedValues.add(Math.abs(mat.get(0, 0).numerator.intValue() * lcmDenominator));
            }
            else
            {
                try
                {
                    Fraction frac = mat.get(i, mat.columnCount() - 1);
                    float result = lcmDenominator * ((float) frac.numerator.intValue() / (float) frac.denominator.intValue());
                    balancedValues.add(Math.abs((int) result));
                }
                catch (Exception e)
                {
                    System.out.println("Balance Error: Unable to Balance");
                    unableToBalance = true;
                }

            }

        }
        if (!unableToBalance)
        {
            StringBuilder ret = new StringBuilder();
            int balanceCounter = 0;
            for (int i = 0; i < tokens.length; i++)
            {
                String process = tokens[i];
                char ch = process.charAt(0);
                while (Character.isDigit(process.charAt(0)))
                {
                    process = process.substring(1);
                }

                if (ch == '=' || tokens[i].compareTo("->") == 0 || ch == '+')
                {
                    if (tokens[i].compareTo("->") == 0)
                    {
                        process = "->";
                    }
                } else {
                    if (balancedValues.get(balanceCounter) != 1 && balancedValues.get(balanceCounter) != 0)
                    {
                        ret.append(balancedValues.get(balanceCounter).toString());
                    }
                    balanceCounter++;
                }
                ret.append(process);
                if (i != tokens.length - 1)
                    ret.append(" ");
            }

            return ret.toString();
        }
        return null;
    }

    public static int gcd(int a, int b)
    {
        if (a < b) return gcd(b, a);
        if (a % b == 0) return b;
        else return gcd(b, a % b);
    }

    public static int lcm(int a, int b)
    {
        return ((a * b) / gcd(a, b));
    }

    public static int lcmofarray(int[] arr, int start, int end)
    {
        if ((end - start) == 1) return lcm(arr[start], arr[end - 1]);
        else return (lcm(arr[start], lcmofarray(arr, start + 1, end)));
    }
}