/**
 * This class is implemented by the <code>TreeNavigator</code> class which
 * holds keywords, left children, right children, and a boolean that states
 * if this <code>TreeNode</code> is a leaf or not. This object is compared
 * given the user input to determine if the cursor would go to the left or
 * right child depending if there is matching text.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class TreeNode {

    // Data fields
    private String[] keywords; // Holds the message only if it is a leaf,
    // otherwise this is a list of words to trigger going down this path.

    private TreeNode left; // Left subtree Also denoted with 0 in file
    private TreeNode right; // Right subtree Also denoted with 1 in file

    // Boolean that determines if this child is a leaf
    private Boolean m_isLeaf = false;

    /**
     * Constructs a new <code>TreeNode</code> object.
     *
     * @param keywords
     *      A string array that holds the different keywords in this
     *      <code>TreeNode</code> object which will be used for comparisons.
     *
     * @param left
     *      The left <code>TreeNode</code> of this <code>TreeNode</code> object.
     * @param right
     *      The right <code>TreeNode</code> of this <code>TreeNode</code>
     *      object.
     * @param isLeaf
     *      A string that shows if this <code>TreeNode</code> object is a
     *      leaf or not.
     *
     * <dt>Postconditions:
     *      <dd>The new <code>TreeNode</code></dd> object has been
     *      constructed</dt>.
     */
    public TreeNode(String[] keywords, TreeNode left, TreeNode right,
                    String isLeaf) {
        this.keywords = keywords;
        this.left = left;
        this.right = right;

        // Get the last string and set if the node is a leaf or nonleaf.
        if (isLeaf.equals("leaf"))
            m_isLeaf = true;
        else
            m_isLeaf = false;
    }

    /**
     * Returns the string array with all the keywords in this
     * <code>TreeNode</code> object.
     *
     * @return
     *      Returns the string array with all the keywords.
     */
    public String[] getKeywordsList() {
        return keywords;
    }

    /**
     * Sets the new string array of keywords of this <code>TreeNode</code>
     * object.
     *
     * @param keywords
     *      The new string array of keywords that will be set to this object.
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns the left child (No) of this <code>TreeNode</code> object.
     * returns null if the child is empty.
     *
     * @return
     *      The left child or the No option of the tree.
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Sets the left child of this <code>TreeNode</code> object.
     *
     * @param left
     *      The <code>TreeNode</code> object that would be set as the left
     *      child of the node.
     */
    public void setLeft(TreeNode left) {
        // If the node is not null, then this node is no longer a leaf
        if (left != null)
            m_isLeaf = false;
        this.left = left;
    }

    /**
     * Returns the right child (Yes) of this <code>TreeNode</code> object.
     * returns null if the child is empty.
     *
     * @return
     *      The right child or the Yes option of the tree.
     */
    public TreeNode getRight() {
        // If the node is not null, then this node is no longer a leaf
        return right;
    }

    /**
     * Sets the right child of this <code>TreeNode</code> object.
     *
     * @param right
     *      The <code>TreeNode</code> object that would be set as the right
     *      child of the node.
     */
    public void setRight(TreeNode right) {
        // If the node is not null, then this node is no longer a leaf
        if (right != null)
            m_isLeaf = false;
        this.right = right;
    }

    /**
     * Returns boolean if the <code>TreeNode</code> is a leaf or not.
     *
     * @return
     *      Returns true if this is a leaf and false if it isn't.
     *
     * <dt>Precondtions:
     *      <dd>The node is initialized.</dd></dt>
     *
     * <dt>Postconditions:
     *      <dd>The tree remains unchanged.</dd></dt>
     */
    public boolean isLeaf() {
        // Leaf if the left and right subtrees are null.
        return (left == null && right == null);
    }

    /**
     * Returns a string representation of this <code>TreeNode</code> object.
     *
     * @return
     *      Returns a string of the properties of this <code>TreeNode</code>
     *      object.
     */
    public String toString() {
        String keys = getKeyWords();

        return String.format("Cursor is " + ((!m_isLeaf) ? "not" : "") + " at" +
                " leaf. Message is '%s'", keys);
    }

    /**
     * Returns a string of all the keywords in this object.
     *
     * @return
     *      A string containing all the keywords.
     */
    public String getKeyWords() {
        String keys = "";
        for (String s: keywords) {
            keys += s + ",";
        }

        return keys;
    }

}
