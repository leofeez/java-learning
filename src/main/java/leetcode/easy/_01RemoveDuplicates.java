package leetcode.easy;

/**
 * @author leofee
 */
public class _01RemoveDuplicates {

    static int nums[] = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};

    public static void main(String[] args) {

        int r = r(nums);
        for (int n : nums) {
            System.out.println(n);
        }
        System.out.println(r);
    }

    /**
     * 双指针：i为慢指针，j为快指针
     * 由于数据有序，当 nums[i] < nums[j]，将 nums[j] 位置放在 nums[i+1] 位置，同时i 和 j 指针往后移动
     * 否则，只移动 j 指针
     */
    public static int r(int[] nums) {
        int i = 0;
        int j = 1;
        for (; j < nums.length; j++) {
            if (nums[i] < nums[j]) nums[++i] = nums[j];
        }
        return i + 1;
    }
}
