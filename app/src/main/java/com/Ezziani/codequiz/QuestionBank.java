package com.Ezziani.codequiz;

import java.util.ArrayList;

public class QuestionBank {

    public static ArrayList<Question> getQuestions(String language, String difficulty) {
        ArrayList<Question> list = new ArrayList<>();

        // ==================== JAVA ====================

        if (language.equals("Java") && difficulty.equals("Easy")) {
            list.add(new Question("What is the output?",
                    "public class Main {\n  public static void main(String[] args) {\n    System.out.println(\"Hello World\");\n  }\n}",
                    "hello world", "Hello World", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint y = 3;\nSystem.out.println(x + y);",
                    "53", "8", "Error", "35", "B"));
            list.add(new Question("What is the output?",
                    "int x = 10;\nSystem.out.println(x * 2);",
                    "10", "2", "20", "Error", "C"));
            list.add(new Question("Find the error:",
                    "public class Main {\n  public static void main(String[] args) {\n    System.out.println(\"Hi\")\n  }\n}",
                    "Missing {", "Missing semicolon after println", "Wrong class name", "No error", "B"));
            list.add(new Question("What is the output?",
                    "boolean x = true;\nSystem.out.println(!x);",
                    "true", "1", "false", "Error", "C"));
            list.add(new Question("What is the output?",
                    "int x = 10 % 3;\nSystem.out.println(x);",
                    "3", "1", "0", "Error", "B"));
            list.add(new Question("What is the output?",
                    "String s = \"Hello\";\nSystem.out.println(s.length());",
                    "4", "6", "5", "Error", "C"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nx += 3;\nSystem.out.println(x);",
                    "5", "3", "8", "Error", "C"));
            list.add(new Question("What is the output?",
                    "System.out.println(2 * 3 + 4);",
                    "14", "10", "Error", "24", "B"));
            list.add(new Question("Find the error:",
                    "int x = \"Hello\";\nSystem.out.println(x);",
                    "Missing semicolon", "Wrong variable type, should be String", "println is wrong", "No error", "B"));
        }

        if (language.equals("Java") && difficulty.equals("Medium")) {
            list.add(new Question("What is the output?",
                    "for (int i = 0; i < 3; i++) {\n  System.out.print(i + \" \");\n}",
                    "1 2 3", "0 1 2", "0 1 2 3", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 0;\nwhile (x < 3) {\n  x++;\n}\nSystem.out.println(x);",
                    "0", "2", "3", "4", "C"));
            list.add(new Question("What is the output?",
                    "static int add(int a, int b) {\n  return a + b;\n}\nSystem.out.println(add(3, 4));",
                    "34", "7", "Error", "0", "B"));
            list.add(new Question("Find the error:",
                    "int[] arr = new int[3];\narr[3] = 10;\nSystem.out.println(arr[3]);",
                    "Wrong array type", "ArrayIndexOutOfBoundsException", "Missing semicolon", "No error", "B"));
            list.add(new Question("What is the output?",
                    "int[] arr = {1, 2, 3};\nSystem.out.println(arr[0] + arr[2]);",
                    "2", "3", "4", "Error", "C"));
            list.add(new Question("What is the output?",
                    "String s = \"Hello World\";\nSystem.out.println(s.substring(0, 5));",
                    "World", "Hello", "Hello World", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 10;\nif (x > 5) {\n  System.out.println(\"Big\");\n} else {\n  System.out.println(\"Small\");\n}",
                    "Small", "Big", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "for (int i = 0; i < 5; i++) {\n  if (i == 3) break;\n  System.out.print(i + \" \");\n}",
                    "0 1 2 3", "0 1 2", "1 2 3", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint y = x++;\nSystem.out.println(y);",
                    "6", "5", "Error", "4", "B"));
            list.add(new Question("Find the error:",
                    "ArrayList<int> list = new ArrayList<>();\nlist.add(1);",
                    "Missing import", "int should be Integer", "add() is wrong", "No error", "B"));
        }

        if (language.equals("Java") && difficulty.equals("Hard")) {
            list.add(new Question("What is the output?",
                    "System.out.println(10 / 3);",
                    "3.33", "3", "4", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 1;\nswitch(x) {\n  case 1: System.out.print(\"A\");\n  case 2: System.out.print(\"B\");\n  break;\n}",
                    "A", "B", "AB", "Error", "C"));
            list.add(new Question("What is the output?",
                    "String a = new String(\"hi\");\nString b = new String(\"hi\");\nSystem.out.println(a == b);",
                    "true", "false", "Error", "hi", "B"));
            list.add(new Question("What is the output?",
                    "int[] arr = {1, 2, 3};\nint[] arr2 = arr;\narr2[0] = 99;\nSystem.out.println(arr[0]);",
                    "1", "99", "Error", "0", "B"));
            list.add(new Question("What is the output?",
                    "System.out.println(\"5\" + 3 + 2);",
                    "10", "532", "53", "Error", "B"));
            list.add(new Question("What is the output?",
                    "System.out.println(5 + 3 + \"2\");",
                    "532", "10", "82", "Error", "C"));
            list.add(new Question("Find the error:",
                    "public class Main {\n  public static void main(String[] args) {\n    int x;\n    System.out.println(x);\n  }\n}",
                    "No error", "x is never assigned a value", "println is wrong", "Missing return", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nSystem.out.println(x > 3 ? \"Yes\" : \"No\");",
                    "No", "Yes", "Error", "true", "B"));
            list.add(new Question("What is the output?",
                    "try {\n  int x = 1 / 0;\n} catch (Exception e) {\n  System.out.println(\"Caught\");\n}",
                    "Error", "Nothing", "Caught", "1", "C"));
            list.add(new Question("Find the error:",
                    "interface Animal {\n  void speak();\n}\nclass Dog extends Animal {\n  void speak() { System.out.println(\"Woof\"); }\n}",
                    "No error", "Dog should use implements not extends", "Interface is wrong", "Missing return type", "B"));
        }

        // ==================== PYTHON ====================

        if (language.equals("Python") && difficulty.equals("Easy")) {
            list.add(new Question("What is the output?",
                    "print(\"Hello World\")",
                    "hello world", "Hello World", "Error", "None", "B"));
            list.add(new Question("What is the output?",
                    "x = 5\ny = 3\nprint(x + y)",
                    "53", "8", "Error", "xy", "B"));
            list.add(new Question("Find the error:",
                    "print(\"Hello World\"",
                    "Wrong quotes", "Missing closing parenthesis", "print is wrong", "No error", "B"));
            list.add(new Question("What is the output?",
                    "x = 10\nprint(x // 3)",
                    "3.33", "3", "4", "Error", "B"));
            list.add(new Question("What is the output?",
                    "x = True\nprint(not x)",
                    "True", "1", "False", "Error", "C"));
            list.add(new Question("What is the output?",
                    "print(10 % 3)",
                    "3", "1", "0", "Error", "B"));
            list.add(new Question("What is the output?",
                    "x = \"Hello\"\nprint(len(x))",
                    "4", "6", "5", "Error", "C"));
            list.add(new Question("What is the output?",
                    "x = 5\nx += 3\nprint(x)",
                    "5", "3", "8", "Error", "C"));
            list.add(new Question("What is the output?",
                    "print(2 ** 3)",
                    "6", "9", "8", "Error", "C"));
            list.add(new Question("Find the error:",
                    "x = 5\nif x > 3\n    print(\"Yes\")",
                    "Wrong indentation", "Missing colon after condition", "print is wrong", "No error", "B"));
        }

        if (language.equals("Python") && difficulty.equals("Medium")) {
            list.add(new Question("What is the output?",
                    "for i in range(3):\n    print(i, end=\" \")",
                    "1 2 3", "0 1 2", "0 1 2 3", "Error", "B"));
            list.add(new Question("What is the output?",
                    "def add(a, b):\n    return a + b\nprint(add(3, 4))",
                    "34", "7", "Error", "0", "B"));
            list.add(new Question("What is the output?",
                    "lst = [1, 2, 3]\nprint(lst[-1])",
                    "1", "Error", "0", "3", "D"));
            list.add(new Question("Find the error:",
                    "def greet()\n    print(\"Hello\")",
                    "Wrong indentation", "Missing colon after greet()", "print is wrong", "No error", "B"));
            list.add(new Question("What is the output?",
                    "lst = [1, 2, 3, 4, 5]\nprint(lst[1:3])",
                    "[1, 2]", "[2, 3]", "[2, 3, 4]", "Error", "B"));
            list.add(new Question("What is the output?",
                    "x = 10\nif x > 5:\n    print(\"Big\")\nelse:\n    print(\"Small\")",
                    "Small", "Big", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "lst = [3, 1, 2]\nlst.sort()\nprint(lst)",
                    "[3, 1, 2]", "[1, 2, 3]", "Error", "[3, 2, 1]", "B"));
            list.add(new Question("What is the output?",
                    "d = {\"a\": 1, \"b\": 2}\nprint(d[\"b\"])",
                    "1", "2", "Error", "b", "B"));
            list.add(new Question("What is the output?",
                    "x = [1, 2, 3]\nprint(sum(x))",
                    "123", "6", "Error", "0", "B"));
            list.add(new Question("Find the error:",
                    "for i in range(3)\n    print(i)",
                    "Wrong indentation", "Missing colon after range(3)", "range is wrong", "No error", "B"));
        }

        if (language.equals("Python") && difficulty.equals("Hard")) {
            list.add(new Question("What is the output?",
                    "x = [1, 2, 3]\ny = x\ny.append(4)\nprint(x)",
                    "[1, 2, 3]", "[1, 2, 3, 4]", "Error", "[4]", "B"));
            list.add(new Question("What is the output?",
                    "print(type(1/2))",
                    "<class 'int'>", "<class 'float'>", "Error", "0", "B"));
            list.add(new Question("What is the output?",
                    "x = lambda a: a * 2\nprint(x(5))",
                    "10", "25", "Error", "a*2", "A"));
            list.add(new Question("Find the error:",
                    "lst = [1, 2, 3]\nprint(lst[5])",
                    "TypeError", "IndexError", "NameError", "No error", "B"));
            list.add(new Question("What is the output?",
                    "d = {'a': 1}\nprint(d.get('b', 0))",
                    "None", "Error", "1", "0", "D"));
            list.add(new Question("What is the output?",
                    "print(list(map(lambda x: x*2, [1,2,3])))",
                    "[1, 2, 3]", "[2, 4, 6]", "Error", "[1, 4, 9]", "B"));
            list.add(new Question("What is the output?",
                    "x = (1, 2, 3)\nx[0] = 99\nprint(x)",
                    "(99, 2, 3)", "(1, 2, 3)", "TypeError", "Error", "C"));
            list.add(new Question("What is the output?",
                    "print([x**2 for x in range(4)])",
                    "[1, 4, 9, 16]", "[0, 1, 4, 9]", "Error", "[0, 1, 2, 3]", "B"));
            list.add(new Question("What is the output?",
                    "def f(x=[]):\n    x.append(1)\n    return x\nprint(f())\nprint(f())",
                    "[1] [1]", "[1] [1, 1]", "Error", "[1, 1] [1]", "B"));
            list.add(new Question("Find the error:",
                    "class Dog:\n    def bark(self):\n        print(\"Woof\")\nd = Dog\nd.bark()",
                    "No error", "Dog is not instantiated, should be Dog()", "bark() is wrong", "Missing self", "B"));
        }

        // ==================== C ====================

        if (language.equals("C") && difficulty.equals("Easy")) {
            list.add(new Question("What is the output?",
                    "#include <stdio.h>\nint main() {\n  printf(\"Hello World\");\n  return 0;\n}",
                    "hello world", "Hello World", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5, y = 3;\nprintf(\"%d\", x + y);",
                    "53", "8", "Error", "35", "B"));
            list.add(new Question("Find the error:",
                    "#include <stdio.h>\nint main() {\n  printf(\"Hi\")\n  return 0;\n}",
                    "Missing #include", "Missing semicolon after printf", "Wrong return type", "No error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 7 % 3;\nprintf(\"%d\", x);",
                    "2", "3", "1", "Error", "C"));
            list.add(new Question("What is the output?",
                    "int x = 10;\nprintf(\"%d\", x / 3);",
                    "3.33", "3", "4", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nx++;\nprintf(\"%d\", x);",
                    "5", "7", "6", "Error", "C"));
            list.add(new Question("What is the output?",
                    "printf(\"%d\", 2 * 3 + 4);",
                    "14", "10", "Error", "24", "B"));
            list.add(new Question("Find the error:",
                    "int main() {\n  printf(\"Hello\");\n}",
                    "Missing #include <stdio.h>", "Missing return 0", "printf is wrong", "No error", "A"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint y = 2;\nprintf(\"%d\", x - y);",
                    "52", "3", "Error", "7", "B"));
            list.add(new Question("What is the output?",
                    "char c = 'A';\nprintf(\"%c\", c);",
                    "65", "A", "Error", "a", "B"));
        }

        if (language.equals("C") && difficulty.equals("Medium")) {
            list.add(new Question("What is the output?",
                    "for (int i = 0; i < 3; i++) {\n  printf(\"%d \", i);\n}",
                    "1 2 3", "0 1 2", "0 1 2 3", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int arr[] = {10, 20, 30};\nprintf(\"%d\", arr[1]);",
                    "10", "20", "30", "Error", "B"));
            list.add(new Question("Find the error:",
                    "int arr[3];\narr[3] = 5;\nprintf(\"%d\", arr[3]);",
                    "Wrong type", "Index out of bounds (undefined behavior)", "Missing semicolon", "No error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nif (x > 3) {\n  printf(\"Yes\");\n} else {\n  printf(\"No\");\n}",
                    "No", "Yes", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "int x = 0;\nwhile (x < 3) x++;\nprintf(\"%d\", x);",
                    "0", "2", "3", "4", "C"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint y = x++;\nprintf(\"%d %d\", x, y);",
                    "5 5", "6 5", "6 6", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int sum = 0;\nfor(int i=1; i<=3; i++) sum += i;\nprintf(\"%d\", sum);",
                    "3", "6", "9", "Error", "B"));
            list.add(new Question("What is the output?",
                    "char str[] = \"Hello\";\nprintf(\"%d\", strlen(str));",
                    "4", "6", "5", "Error", "C"));
            list.add(new Question("Find the error:",
                    "int x = 5;\nif (x = 3) {\n  printf(\"Yes\");\n}",
                    "No error", "= should be == inside if condition", "Missing else", "Wrong type", "B"));
            list.add(new Question("What is the output?",
                    "int x = 10;\nswitch(x) {\n  case 10: printf(\"Ten\"); break;\n  default: printf(\"Other\");\n}",
                    "Other", "Ten", "Error", "Nothing", "B"));
        }

        if (language.equals("C") && difficulty.equals("Hard")) {
            list.add(new Question("What is the output?",
                    "int x = 5;\nint *p = &x;\nprintf(\"%d\", *p);",
                    "Address of x", "5", "Error", "0", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint y = x++;\nprintf(\"%d\", y);",
                    "6", "5", "Error", "4", "B"));
            list.add(new Question("Find the error:",
                    "int *p;\n*p = 10;\nprintf(\"%d\", *p);",
                    "Wrong type", "Uninitialized pointer (undefined behavior)", "Missing semicolon", "No error", "B"));
            list.add(new Question("What is the output?",
                    "printf(\"%d\", sizeof(int));",
                    "2", "8", "4", "Depends on system", "D"));
            list.add(new Question("What is the output?",
                    "int x = 1;\nswitch(x) {\n  case 1: printf(\"A\");\n  case 2: printf(\"B\");\n  break;\n}",
                    "A", "B", "AB", "Error", "C"));
            list.add(new Question("What is the output?",
                    "int arr[] = {1,2,3};\nint *p = arr;\nprintf(\"%d\", *(p+1));",
                    "1", "2", "3", "Error", "B"));
            list.add(new Question("Find the error:",
                    "void add(int a, int b) {\n  return a + b;\n}",
                    "No error", "void function cannot return a value", "Missing semicolon", "Wrong parameters", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nprintf(\"%d\", x << 1);",
                    "5", "10", "25", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int a = 5, b = 3;\nprintf(\"%d\", a & b);",
                    "8", "2", "1", "Error", "C"));
            list.add(new Question("Find the error:",
                    "char *s = \"Hello\";\ns[0] = 'h';\nprintf(\"%s\", s);",
                    "No error", "String literals are read-only (undefined behavior)", "Wrong format specifier", "Missing semicolon", "B"));
        }

        // ==================== C++ ====================

        if (language.equals("C++") && difficulty.equals("Easy")) {
            list.add(new Question("What is the output?",
                    "#include <iostream>\nusing namespace std;\nint main() {\n  cout << \"Hello World\";\n}",
                    "hello world", "Hello World", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5, y = 3;\ncout << x + y;",
                    "53", "8", "Error", "35", "B"));
            list.add(new Question("Find the error:",
                    "#include <iostream>\nint main() {\n  cout << \"Hi\";\n}",
                    "Missing using namespace std", "Missing return 0", "cout is wrong", "No error", "A"));
            list.add(new Question("What is the output?",
                    "int x = 10;\ncout << x / 3;",
                    "3.33", "3", "4", "Error", "B"));
            list.add(new Question("What is the output?",
                    "bool x = true;\ncout << !x;",
                    "true", "1", "0", "Error", "C"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nx++;\ncout << x;",
                    "5", "7", "6", "Error", "C"));
            list.add(new Question("What is the output?",
                    "cout << 2 * 3 + 4;",
                    "14", "10", "Error", "24", "B"));
            list.add(new Question("What is the output?",
                    "int x = 10 % 3;\ncout << x;",
                    "3", "1", "0", "Error", "B"));
            list.add(new Question("Find the error:",
                    "int main() {\n  int x = 5\n  cout << x;\n}",
                    "Missing #include", "Missing semicolon after int x = 5", "cout is wrong", "No error", "B"));
            list.add(new Question("What is the output?",
                    "char c = 'A';\ncout << c;",
                    "65", "A", "Error", "a", "B"));
        }

        if (language.equals("C++") && difficulty.equals("Medium")) {
            list.add(new Question("What is the output?",
                    "for (int i = 0; i < 3; i++) {\n  cout << i << \" \";\n}",
                    "1 2 3", "0 1 2", "0 1 2 3", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int arr[] = {10, 20, 30};\ncout << arr[1];",
                    "10", "20", "30", "Error", "B"));
            list.add(new Question("What is the output?",
                    "string s = \"Hello\";\ncout << s.length();",
                    "4", "6", "5", "Error", "C"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint y = x++;\ncout << y;",
                    "6", "5", "Error", "4", "B"));
            list.add(new Question("Find the error:",
                    "vector<int> v;\nv.push_back(1);\ncout << v[1];",
                    "No error", "Index 1 is out of bounds, only index 0 exists", "push_back is wrong", "Wrong type", "B"));
            list.add(new Question("What is the output?",
                    "int x = 10;\nif (x > 5) {\n  cout << \"Big\";\n} else {\n  cout << \"Small\";\n}",
                    "Small", "Big", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "int sum = 0;\nfor(int i=1; i<=3; i++) sum += i;\ncout << sum;",
                    "3", "6", "9", "Error", "B"));
            list.add(new Question("What is the output?",
                    "int x = 1;\nswitch(x) {\n  case 1: cout << \"A\";\n  case 2: cout << \"B\";\n  break;\n}",
                    "A", "B", "AB", "Error", "C"));
            list.add(new Question("Find the error:",
                    "int x = 5;\nif (x = 3) {\n  cout << \"Yes\";\n}",
                    "No error", "= should be == inside condition", "Missing else", "Wrong type", "B"));
            list.add(new Question("What is the output?",
                    "while(false) {\n  cout << \"Hello\";\n}\ncout << \"Done\";",
                    "Hello Done", "Hello", "Done", "Error", "C"));
        }

        if (language.equals("C++") && difficulty.equals("Hard")) {
            list.add(new Question("What is the output?",
                    "int x = 5;\nint *p = &x;\ncout << *p;",
                    "Address of x", "5", "Error", "0", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\nint &ref = x;\nref = 10;\ncout << x;",
                    "5", "10", "Error", "0", "B"));
            list.add(new Question("Find the error:",
                    "int *p = new int;\n*p = 5;\ncout << *p;",
                    "No error, but memory leak (missing delete)", "p is not initialized", "Wrong syntax", "No error at all", "A"));
            list.add(new Question("What is the output?",
                    "class Dog {\npublic:\n  void bark() { cout << \"Woof\"; }\n};\nDog d;\nd.bark();",
                    "Error", "Dog", "Woof", "Nothing", "C"));
            list.add(new Question("What is the output?",
                    "cout << sizeof(double);",
                    "4", "2", "8", "Depends on system", "C"));
            list.add(new Question("What is the output?",
                    "int x = 5;\ncout << (x > 3 ? \"Yes\" : \"No\");",
                    "No", "Yes", "Error", "true", "B"));
            list.add(new Question("Find the error:",
                    "class Animal {\n  virtual void speak() = 0;\n};\nAnimal a;\na.speak();",
                    "No error", "Cannot instantiate abstract class", "speak() is wrong", "Missing return", "B"));
            list.add(new Question("What is the output?",
                    "int x = 5;\ncout << (x & 3);",
                    "8", "2", "1", "Error", "C"));
            list.add(new Question("What is the output?",
                    "try {\n  throw 42;\n} catch (int e) {\n  cout << e;\n}",
                    "Error", "Nothing", "42", "0", "C"));
            list.add(new Question("What is the output?",
                    "vector<int> v = {1,2,3};\nfor(auto x : v) cout << x;\n",
                    "1 2 3", "123", "Error", "Nothing", "B"));
        }

        // ==================== JAVASCRIPT ====================

        if (language.equals("JavaScript") && difficulty.equals("Easy")) {
            list.add(new Question("What is the output?",
                    "console.log(\"Hello World\");",
                    "hello world", "Hello World", "Error", "Nothing", "B"));
            list.add(new Question("What is the output?",
                    "let x = 5;\nlet y = 3;\nconsole.log(x + y);",
                    "53", "8", "Error", "xy", "B"));
            list.add(new Question("What is the output?",
                    "console.log(typeof 42);",
                    "int", "number", "float", "Error", "B"));
            list.add(new Question("Find the error:",
                    "console.log(\"Hello World\"",
                    "Wrong quotes", "Missing closing parenthesis", "console is wrong", "No error", "B"));
            list.add(new Question("What is the output?",
                    "console.log(10 % 3);",
                    "3", "1", "0", "Error", "B"));
            list.add(new Question("What is the output?",
                    "let x = true;\nconsole.log(!x);",
                    "true", "1", "false", "Error", "C"));
            list.add(new Question("What is the output?",
                    "let s = \"Hello\";\nconsole.log(s.length);",
                    "4", "6", "5", "Error", "C"));
            list.add(new Question("What is the output?",
                    "console.log(2 ** 3);",
                    "6", "9", "8", "Error", "C"));
            list.add(new Question("What is the output?",
                    "let x = 5;\nx += 3;\nconsole.log(x);",
                    "5", "3", "8", "Error", "C"));
            list.add(new Question("Find the error:",
                    "let x = 5;\nif x > 3 {\n  console.log(\"Yes\");\n}",
                    "Missing semicolon", "Condition must be in parentheses", "console is wrong", "No error", "B"));
        }

        if (language.equals("JavaScript") && difficulty.equals("Medium")) {
            list.add(new Question("What is the output?",
                    "for (let i = 0; i < 3; i++) {\n  console.log(i);\n}",
                    "1 2 3", "0 1 2", "0 1 2 3", "Error", "B"));
            list.add(new Question("What is the output?",
                    "let arr = [1, 2, 3];\nconsole.log(arr[arr.length - 1]);",
                    "1", "2", "4", "3", "D"));
            list.add(new Question("What is the output?",
                    "console.log(1 == \"1\");",
                    "false", "true", "Error", "undefined", "B"));
            list.add(new Question("What is the output?",
                    "console.log(1 === \"1\");",
                    "false", "true", "Error", "undefined", "A"));
            list.add(new Question("What is the output?",
                    "let x = null;\nconsole.log(typeof x);",
                    "null", "undefined", "object", "Error", "C"));
            list.add(new Question("What is the output?",
                    "function add(a, b) { return a + b; }\nconsole.log(add(3, 4));",
                    "34", "7", "Error", "0", "B"));
            list.add(new Question("What is the output?",
                    "let arr = [1,2,3];\narr.push(4);\nconsole.log(arr.length);",
                    "3", "5", "4", "Error", "C"));
            list.add(new Question("Find the error:",
                    "const x = 5;\nx = 10;\nconsole.log(x);",
                    "No error", "Cannot reassign a const variable", "console is wrong", "Missing semicolon", "B"));
            list.add(new Question("What is the output?",
                    "let x = \"5\";\nconsole.log(x * 2);",
                    "55", "10", "Error", "NaN", "B"));
            list.add(new Question("What is the output?",
                    "console.log([] + []);",
                    "[]", "Error", "undefined", "", "D"));
        }

        if (language.equals("JavaScript") && difficulty.equals("Hard")) {
            list.add(new Question("What is the output?",
                    "console.log(typeof NaN);",
                    "NaN", "undefined", "number", "Error", "C"));
            list.add(new Question("What is the output?",
                    "console.log(0.1 + 0.2 === 0.3);",
                    "true", "false", "Error", "undefined", "B"));
            list.add(new Question("What is the output?",
                    "let x = [1,2,3];\nlet y = x;\ny.push(4);\nconsole.log(x.length);",
                    "3", "4", "Error", "undefined", "B"));
            list.add(new Question("What is the output?",
                    "console.log(!!\"hello\");",
                    "hello", "false", "true", "Error", "C"));
            list.add(new Question("What is the output?",
                    "const f = () => arguments[0];\nconsole.log(f(42));",
                    "42", "undefined", "ReferenceError", "Error", "C"));
            list.add(new Question("What is the output?",
                    "console.log(1 + \"2\" + 3);",
                    "6", "123", "12", "Error", "B"));
            list.add(new Question("What is the output?",
                    "let x = 0;\nconsole.log(x || \"default\");",
                    "0", "default", "false", "Error", "B"));
            list.add(new Question("Find the error:",
                    "async function f() {\n  return 42;\n}\nconsole.log(f());",
                    "No error", "Returns a Promise not 42 directly", "async is wrong", "Missing await", "B"));
            list.add(new Question("What is the output?",
                    "let a = [1,2,3];\nconsole.log(a.map(x => x * 2));",
                    "[1, 2, 3]", "[2, 4, 6]", "Error", "undefined", "B"));
            list.add(new Question("What is the output?",
                    "console.log([] == false);",
                    "false", "true", "Error", "undefined", "B"));
        }

        // ==================== SQL ====================

        if (language.equals("SQL") && difficulty.equals("Easy")) {
            list.add(new Question("What does this query return?",
                    "SELECT * FROM users;",
                    "Deletes all users", "Returns all columns and rows from users", "Updates users", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT name FROM users;",
                    "All columns", "Only the name column", "Deletes name", "Error", "B"));
            list.add(new Question("Find the error:",
                    "SELEC * FROM users;",
                    "Missing semicolon", "SELEC is misspelled, should be SELECT", "FROM is wrong", "No error", "B"));
            list.add(new Question("What does this query do?",
                    "DELETE FROM users WHERE id = 1;",
                    "Updates user with id 1", "Selects user with id 1", "Deletes user with id 1", "Error", "C"));
            list.add(new Question("What does this query do?",
                    "INSERT INTO users (name, age) VALUES ('Alice', 25);",
                    "Updates Alice", "Inserts a new row with name Alice and age 25", "Deletes Alice", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT COUNT(*) FROM users;",
                    "All users", "The number of rows in users", "Names of users", "Error", "B"));
            list.add(new Question("What does this query do?",
                    "UPDATE users SET age = 30 WHERE name = 'Alice';",
                    "Deletes Alice", "Sets age to 30 for Alice", "Inserts Alice", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT * FROM users ORDER BY age ASC;",
                    "Users in random order", "Users ordered by age descending", "Users ordered by age ascending", "Error", "C"));
            list.add(new Question("Find the error:",
                    "SELECT * FROM users WHERE age > '25';",
                    "No error", "Age is a number, should not use quotes", "WHERE is wrong", "Missing semicolon", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT DISTINCT city FROM users;",
                    "All cities including duplicates", "Unique cities only", "Number of cities", "Error", "B"));
        }

        if (language.equals("SQL") && difficulty.equals("Medium")) {
            list.add(new Question("What does this query return?",
                    "SELECT * FROM users WHERE age > 18 AND city = 'Paris';",
                    "Users older than 18 or from Paris", "Users older than 18 from Paris", "All users", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT name, COUNT(*) FROM orders GROUP BY name;",
                    "All orders", "Number of orders per name", "Names only", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT * FROM users LIMIT 5;",
                    "Last 5 users", "First 5 users", "All users", "Error", "B"));
            list.add(new Question("What does this query do?",
                    "ALTER TABLE users ADD COLUMN email VARCHAR(100);",
                    "Deletes email column", "Adds email column to users table", "Updates email", "Error", "B"));
            list.add(new Question("Find the error:",
                    "SELECT * FROM users HAVING age > 18;",
                    "No error", "HAVING is used with GROUP BY, should use WHERE", "FROM is wrong", "Missing semicolon", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT * FROM users WHERE name LIKE 'A%';",
                    "Users whose name contains A", "Users whose name starts with A", "Users named A", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT MAX(age) FROM users;",
                    "All ages", "The minimum age", "The maximum age", "Error", "C"));
            list.add(new Question("What does this query do?",
                    "DROP TABLE users;",
                    "Clears all rows but keeps the table", "Deletes the entire users table", "Renames the table", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT * FROM users WHERE age BETWEEN 18 AND 30;",
                    "Users younger than 18 or older than 30", "Users aged between 18 and 30 inclusive", "Error", "All users", "B"));
            list.add(new Question("Find the error:",
                    "SELECT name FROM users GROUP BY city;",
                    "No error", "name must appear in GROUP BY or aggregate function", "FROM is wrong", "Missing semicolon", "B"));
        }

        if (language.equals("SQL") && difficulty.equals("Hard")) {
            list.add(new Question("What does this query return?",
                    "SELECT u.name, o.total\nFROM users u\nINNER JOIN orders o ON u.id = o.user_id;",
                    "All users even without orders", "Only users who have orders", "All orders", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT u.name, o.total\nFROM users u\nLEFT JOIN orders o ON u.id = o.user_id;",
                    "Only users who have orders", "All users, with NULL for those without orders", "All orders", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT name, AVG(score)\nFROM results\nGROUP BY name\nHAVING AVG(score) > 80;",
                    "All students", "Students with average score above 80", "Students with score exactly 80", "Error", "B"));
            list.add(new Question("Find the error:",
                    "SELECT * FROM users WHERE id IN (SELECT id FROM orders WHERE total > 100);",
                    "No error, this is a valid subquery", "IN is wrong", "Subquery is missing FROM", "Missing semicolon", "A"));
            list.add(new Question("What does this query do?",
                    "CREATE INDEX idx_name ON users(name);",
                    "Deletes the name column", "Creates an index to speed up searches on name", "Updates all names", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT name FROM users\nUNION\nSELECT name FROM admins;",
                    "Only names in both tables", "All names from both tables without duplicates", "All names with duplicates", "Error", "B"));
            list.add(new Question("What does this query return?",
                    "SELECT name FROM users\nINTERSECT\nSELECT name FROM admins;",
                    "Names only in users", "Names only in admins", "Names that appear in both tables", "Error", "C"));
            list.add(new Question("Find the error:",
                    "UPDATE users SET age = age + 1;",
                    "Missing WHERE clause (updates all rows)", "No error", "SET is wrong", "Missing semicolon", "A"));
            list.add(new Question("What does this query return?",
                    "SELECT name, RANK() OVER (ORDER BY score DESC) FROM results;",
                    "Names sorted alphabetically", "Names with their rank by score", "Error", "Top name only", "B"));
            list.add(new Question("What does this query do?",
                    "BEGIN TRANSACTION;\nUPDATE accounts SET balance = balance - 100 WHERE id = 1;\nUPDATE accounts SET balance = balance + 100 WHERE id = 2;\nCOMMIT;",
                    "Deletes two accounts", "Transfers 100 from account 1 to account 2 atomically", "Updates only account 1", "Error", "B"));
        }

        return list;
    }
}