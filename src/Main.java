import java.util.Scanner;

public class Main {

    // ---------- MAIN MENU ----------
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);

        System.out.println("Choose Task:");
        System.out.println("1 - Anagram Sort Checker");
        System.out.println("2 - K-th Smallest Element");
        System.out.println("3 - Median Element");
        System.out.println("4 - Optimal Shipping Capacity");

        int selectedTask = inputReader.nextInt();
        inputReader.nextLine();

        switch (selectedTask) {
            case 1:
                checkAnagramStrings(inputReader);
                break;
            case 2:
                findKthSmallestNumber(inputReader);
                break;
            case 3:
                findMedianValue(inputReader);
                break;
            case 4:
                calculateMinimumShippingCapacity(inputReader);
                break;
            default:
                System.out.println("Invalid task selection.");
        }

        inputReader.close();
    }

    // ---------- TASK 1 ----------
    public static void checkAnagramStrings(Scanner inputReader) {
        String firstWord = inputReader.nextLine();
        String secondWord = inputReader.nextLine();

        if (firstWord.length() != secondWord.length()) {
            System.out.println("NO");
            return;
        }

        char[] firstCharacters = firstWord.toCharArray();
        char[] secondCharacters = secondWord.toCharArray();

        mergeSortCharacters(firstCharacters, 0, firstCharacters.length - 1);
        mergeSortCharacters(secondCharacters, 0, secondCharacters.length - 1);

        boolean areAnagrams = true;

        for (int index = 0; index < firstCharacters.length; index++) {
            if (firstCharacters[index] != secondCharacters[index]) {
                areAnagrams = false;
                break;
            }
        }

        if (areAnagrams) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    // ---------- TASK 2 ----------
    public static void findKthSmallestNumber(Scanner inputReader) {
        int arraySize = inputReader.nextInt();
        int[] numberArray = new int[arraySize];

        for (int index = 0; index < arraySize; index++) {
            numberArray[index] = inputReader.nextInt();
        }

        int kthPosition = inputReader.nextInt();

        mergeSortNumbers(numberArray, 0, arraySize - 1);

        System.out.println(numberArray[kthPosition - 1]);
    }

    // ---------- TASK 3 ----------
    public static void findMedianValue(Scanner inputReader) {
        int arraySize = inputReader.nextInt();
        int[] numberArray = new int[arraySize];

        for (int index = 0; index < arraySize; index++) {
            numberArray[index] = inputReader.nextInt();
        }

        mergeSortNumbers(numberArray, 0, arraySize - 1);

        int medianIndex = arraySize / 2;

        System.out.println(numberArray[medianIndex]);
    }

    // ---------- TASK 4 ----------
    public static void calculateMinimumShippingCapacity(Scanner inputReader) {
        int packageCount = inputReader.nextInt();
        int[] packageWeights = new int[packageCount];

        for (int index = 0; index < packageCount; index++) {
            packageWeights[index] = inputReader.nextInt();
        }

        int allowedDays = inputReader.nextInt();

        int minimumCapacity = findMinimumTruckCapacity(packageWeights, allowedDays);

        System.out.println(minimumCapacity);
    }

    public static int findMinimumTruckCapacity(int[] packageWeights, int allowedDays) {
        int lowestCapacity = findMaximumWeight(packageWeights);
        int highestCapacity = findTotalWeight(packageWeights);

        while (lowestCapacity < highestCapacity) {
            int middleCapacity = lowestCapacity + (highestCapacity - lowestCapacity) / 2;

            if (canShipWithinDays(packageWeights, allowedDays, middleCapacity)) {
                highestCapacity = middleCapacity;
            } else {
                lowestCapacity = middleCapacity + 1;
            }
        }

        return lowestCapacity;
    }

    public static boolean canShipWithinDays(int[] packageWeights, int allowedDays, int truckCapacity) {
        int requiredDays = 1;
        int currentLoad = 0;

        for (int weight : packageWeights) {
            if (currentLoad + weight > truckCapacity) {
                requiredDays++;
                currentLoad = 0;
            }
            currentLoad += weight;
        }

        return requiredDays <= allowedDays;
    }

    public static int findMaximumWeight(int[] packageWeights) {
        int maximumWeight = packageWeights[0];

        for (int weight : packageWeights) {
            if (weight > maximumWeight) {
                maximumWeight = weight;
            }
        }

        return maximumWeight;
    }

    public static int findTotalWeight(int[] packageWeights) {
        int totalWeight = 0;

        for (int weight : packageWeights) {
            totalWeight += weight;
        }

        return totalWeight;
    }

    // ---------- MERGE SORT FOR INTEGERS ----------
    public static void mergeSortNumbers(int[] numberArray, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;

            mergeSortNumbers(numberArray, leftIndex, middleIndex);
            mergeSortNumbers(numberArray, middleIndex + 1, rightIndex);

            mergeNumberSections(numberArray, leftIndex, middleIndex, rightIndex);
        }
    }

    public static void mergeNumberSections(int[] numberArray, int leftIndex, int middleIndex, int rightIndex) {
        int leftSize = middleIndex - leftIndex + 1;
        int rightSize = rightIndex - middleIndex;

        int[] leftPart = new int[leftSize];
        int[] rightPart = new int[rightSize];

        for (int index = 0; index < leftSize; index++) {
            leftPart[index] = numberArray[leftIndex + index];
        }

        for (int index = 0; index < rightSize; index++) {
            rightPart[index] = numberArray[middleIndex + 1 + index];
        }

        int leftPointer = 0;
        int rightPointer = 0;
        int mergedPointer = leftIndex;

        while (leftPointer < leftSize && rightPointer < rightSize) {
            if (leftPart[leftPointer] <= rightPart[rightPointer]) {
                numberArray[mergedPointer] = leftPart[leftPointer];
                leftPointer++;
            } else {
                numberArray[mergedPointer] = rightPart[rightPointer];
                rightPointer++;
            }
            mergedPointer++;
        }

        while (leftPointer < leftSize) {
            numberArray[mergedPointer] = leftPart[leftPointer];
            leftPointer++;
            mergedPointer++;
        }

        while (rightPointer < rightSize) {
            numberArray[mergedPointer] = rightPart[rightPointer];
            rightPointer++;
            mergedPointer++;
        }
    }

    // ---------- MERGE SORT FOR CHARACTERS ----------
    public static void mergeSortCharacters(char[] characterArray, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;

            mergeSortCharacters(characterArray, leftIndex, middleIndex);
            mergeSortCharacters(characterArray, middleIndex + 1, rightIndex);

            mergeCharacterSections(characterArray, leftIndex, middleIndex, rightIndex);
        }
    }

    public static void mergeCharacterSections(char[] characterArray, int leftIndex, int middleIndex, int rightIndex) {
        int leftSize = middleIndex - leftIndex + 1;
        int rightSize = rightIndex - middleIndex;

        char[] leftPart = new char[leftSize];
        char[] rightPart = new char[rightSize];

        for (int index = 0; index < leftSize; index++) {
            leftPart[index] = characterArray[leftIndex + index];
        }

        for (int index = 0; index < rightSize; index++) {
            rightPart[index] = characterArray[middleIndex + 1 + index];
        }

        int leftPointer = 0;
        int rightPointer = 0;
        int mergedPointer = leftIndex;

        while (leftPointer < leftSize && rightPointer < rightSize) {
            if (leftPart[leftPointer] <= rightPart[rightPointer]) {
                characterArray[mergedPointer] = leftPart[leftPointer];
                leftPointer++;
            } else {
                characterArray[mergedPointer] = rightPart[rightPointer];
                rightPointer++;
            }
            mergedPointer++;
        }

        while (leftPointer < leftSize) {
            characterArray[mergedPointer] = leftPart[leftPointer];
            leftPointer++;
            mergedPointer++;
        }

        while (rightPointer < rightSize) {
            characterArray[mergedPointer] = rightPart[rightPointer];
            rightPointer++;
            mergedPointer++;
        }
    }
}