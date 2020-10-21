import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class HakoChara implements Common {
    // �ړ��X�s�[�h
    private static final int SPEED = 4;
    // �ړ��m��
    public static final double PROB_MOVE = 0.02;

    // �C���[�W
    private Image charaImage;

    // �L�����N�^�[�ԍ�
    private int charaNo;

    // ���W
    private int x, y; // �P�ʁF�}�X
    private int px, py; // �P�ʁF�s�N�Z��

    // �����Ă�������iLEFT,RIGHT,UP,DOWN�̂ǂꂩ�j
    private int direction;
    // �A�j���[�V�����J�E���^
    private int count;

    // �ړ����i�X�N���[�����j��
    private boolean isMoving;
    // �ړ����̏ꍇ�̈ړ��s�N�Z����
    private int movingLength;

    // �ړ����@
    private int moveType;


    // �͂Ȃ����b�Z�[�W
    private String message;

    // �A�j���[�V�����p�X���b�h
    private Thread threadAnime;
    // �}�b�v�ւ̎Q��
    private HakoMap map;


    public HakoChara(int x, int y, String filename, int charaNo, int direction, int moveType, HakoMap map) {
        //
        this.x = x;
        this.y = y;
        px = x * CS;
        py = y * CS;

        this.charaNo = charaNo;
        this.direction = direction;
        count = 0;
        this.moveType = moveType;
        this.map = map;

        //
        loadImage(filename);


        // thread start
        threadAnime = new Thread(new AnimationThread());
        threadAnime.start();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // count��direction�̒l�ɉ����ĕ\������摜��؂�ւ���
        g.drawImage(charaImage, px + offsetX, py + offsetY, px + offsetX + CS, py + offsetY + CS, count * CS,
                direction * CS, CS + count * CS, direction * CS + CS, null);
    }

    /**
     * 
     * �ړ������B
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    public boolean move() {
        switch (direction) {
            case LEFT:
                if (moveLeft()) {
                    // The move is complete
                    return true;
                }
                break;
            case RIGHT:
                if (moveRight()) {
                    return true;
                }
                break;
            case UP:
                if (moveUp()) {
                    return true;
                }
                break;
            case DOWN:
                if (moveDown()) {
                    return true;
                }
                break;
        }

        // The move is not complete
        return false;
    }

    /**
     * ���ֈړ�����B
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveLeft() {
        // 1�}�X��̍��W
        int nextX = x - 1;
        int nextY = y;
        if (nextX < 0)
            nextX = 0;
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            px -= HakoChara.SPEED;
            if (px < 0)
                px = 0;
            // �ړ����������Z
            movingLength += HakoChara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                x--;
                if (x < 0)
                    x = 0;
                px = x * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            // ���̈ʒu�ɖ߂�
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * �E�ֈړ�����B
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveRight() {
        // 1�}�X��̍��W
        int nextX = x + 1;
        int nextY = y;
        if (nextX > map.getCol() - 1)
            nextX = map.getCol() - 1;
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            px += HakoChara.SPEED;
            if (px > map.getWidth() - CS)
                px = map.getWidth() - CS;
            // �ړ����������Z
            movingLength += HakoChara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                x++;
                if (x > map.getCol() - 1)
                    x = map.getCol() - 1;
                px = x * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * ��ֈړ�����B
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveUp() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y - 1;
        if (nextY < 0)
            nextY = 0;
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            py -= HakoChara.SPEED;
            if (py < 0)
                py = 0;
            // �ړ����������Z
            movingLength += HakoChara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                y--;
                if (y < 0)
                    y = 0;
                py = y * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * ���ֈړ�����B
     * 
     * @return 1�}�X�ړ�������������true��Ԃ��B�ړ�����false��Ԃ��B
     */
    private boolean moveDown() {
        // 1�}�X��̍��W
        int nextX = x;
        int nextY = y + 1;
        if (nextY > map.getRow() - 1)
            nextY = map.getRow() - 1;
        // ���̏ꏊ�ɏ�Q�����Ȃ���Έړ����J�n
        if (!map.isHit(nextX, nextY)) {
            // SPEED�s�N�Z�����ړ�
            py += HakoChara.SPEED;
            if (py > map.getHeight() - CS)
                py = map.getHeight() - CS;
            // �ړ����������Z
            movingLength += HakoChara.SPEED;
            // �ړ���1�}�X���𒴂��Ă�����
            if (movingLength >= CS) {
                // �ړ�����
                y++;
                if (y > map.getRow() - 1)
                    y = map.getRow() - 1;
                py = y * CS;
                // �ړ�������
                isMoving = false;
                return true;
            }
        } else {
            isMoving = false;
            px = x * CS;
            py = y * CS;
        }

        return false;
    }

    /**
     * �L�����N�^�[�������Ă�������̂ƂȂ�ɃL�����N�^�[�����邩���ׂ�
     * 
     * @return �L�����N�^�[�������炻��Chara�I�u�W�F�N�g��Ԃ�
     */
    public HakoChara talkWith() {
        int nextX = 0;
        int nextY = 0;
        // �L�����N�^�[�̌����Ă��������1���ƂȂ�̍��W
        switch (direction) {
            case LEFT:
                nextX = x - 1;
                nextY = y;
                break;
            case RIGHT:
                nextX = x + 1;
                nextY = y;
                break;
            case UP:
                nextX = x;
                nextY = y - 1;
                break;
            case DOWN:
                nextX = x;
                nextY = y + 1;
                break;
        }
        // ���̕����ɃL�����N�^�[�����邩���ׂ�
        HakoChara chara;
        chara = map.charaCheck(nextX, nextY);
        // �L�����N�^�[������Θb���������L�����N�^�[�̕��֌�����
        if (chara != null) {
            switch (direction) {
                case LEFT:
                    chara.setDirection(RIGHT);
                    break;
                case RIGHT:
                    chara.setDirection(LEFT);
                    break;
                case UP:
                    chara.setDirection(DOWN);
                    break;
                case DOWN:
                    chara.setDirection(UP);
                    break;
            }
        }

        return chara;
    }

    private void loadImage(String filename) {
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        charaImage = icon.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public void setDirection(int dir) {
        direction = dir;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean flag) {
        isMoving = flag;
        // �ړ�������������
        movingLength = 0;
    }

    /**
     * �L�����N�^�[�̃��b�Z�[�W��Ԃ�
     * 
     * @return ���b�Z�[�W
     */
    public String getMessage() {
        return message;
    }

    /**
     * �L�����N�^�[�̃��b�Z�[�W��Ԃ�
     * 
     * @param message ���b�Z�[�W
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public int getMoveType() {
        return moveType;
    }

    // �A�j���[�V�����N���X
    private class AnimationThread extends Thread {
        public void run() {
            while (true) {
                // count��؂�ւ���
                if (count == 0) {
                    count = 2;
                } else if (count == 2) {
                    count = 0;
                }

                // 300�~���b�x�~��300�~���b�����ɃL�����N�^�[�̊G��؂�ւ���
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}