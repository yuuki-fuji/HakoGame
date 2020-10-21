import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class HakoPanel extends JPanel implements KeyListener, Runnable, Common {
    // �p�l���T�C�Y
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    private HakoMap map; // �}�b�v
    private HakoChara hero; // ��l��

    private HakoChara sensei; //�搶
    private HakoChara ara; //���k
    private HakoChara oo; //
    private HakoChara oshi; //
    private HakoChara oni; //
    private HakoChara kama; //
    private HakoChara kino; //
    private HakoChara kugi; //
    private HakoChara kubo; //
    private HakoChara kuro; //
    private HakoChara naka; //
    private HakoChara hashi; //
    private HakoChara hime; //
    private HakoChara fuji; //
    private HakoChara mae; //
    private HakoChara yoshi; //
    private HakoChara matsuda; //
    private HakoChara ono; //
    private HakoChara takuya; //
    private HakoChara youko; //
    private HakoChara naga; //
    
    private HakoChara shironeko; //
    private HakoChara nekonin1; //
    private HakoChara sensei2; //
    private HakoChara sensei3; //

    // �A�N�V�����L�[
    private HakoActionKey leftKey;
    private HakoActionKey rightKey;
    private HakoActionKey upKey;
    private HakoActionKey downKey;
    private HakoActionKey spaceKey;

    // �Q�[�����[�v�J�n
    private Thread gameLoop;

    // ����������
    private Random rand = new Random();

    // �E�B���h�E
    private MessageWindow messageWindow;
    // �E�B���h�E��\������̈�
    private static Rectangle WND_RECT = new Rectangle(62, 324, 356, 140);

    public HakoPanel() {

        // �p�l���̐����T�C�Y��ݒ�
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // �p�l�����L�[������󂯕t����悤�ɓo�^����
        setFocusable(true);
        addKeyListener(this);

        // �A�N�V�����L�[���쐬
        leftKey = new HakoActionKey();
        rightKey = new HakoActionKey();
        upKey = new HakoActionKey();
        downKey = new HakoActionKey();
        spaceKey = new HakoActionKey(HakoActionKey.DETECT_INITIAL_PRESS_ONLY);

        // �}�b�v�A��l���A���k�̍쐬(�t�@�C���̓ǂݍ���)
        // HakoChara(x,y,filename,charaNo,direction,moveType,map)
        map = new HakoMap("map/map.dat", this);
        hero = new HakoChara(7, 7,  "image/hero1.png", 0, 0, 0, map);
        sensei = new HakoChara(7, 19, "image/kabotya1.png", 1, 0, 0, map);
        ara = new HakoChara(12, 15, "image/ara.png", 2, 0, 0, map);
        oo = new HakoChara(9, 15, "image/oo.png", 3, 0, 0, map);
        oshi = new HakoChara(6, 15, "image/oshi.png", 4, 0, 0, map);
        oni = new HakoChara(3, 15, "image/oni.png", 5, 0, 0, map);
        kama = new HakoChara(12, 12, "image/image2.png", 6, 0, 0, map);
        kino = new HakoChara(9, 12, "image/kino.png", 7, 0, 0, map);
        kugi = new HakoChara(6, 12, "image/kugi.png", 8, 0, 0, map);
        kubo = new HakoChara(3, 12, "image/kubo.png", 9, 0, 0, map);
        kuro = new HakoChara(12, 9, "image/kuro.png", 10, 0, 0, map);
        naka = new HakoChara(9, 9, "image/naka.png", 11, 0, 0, map);
        hashi = new HakoChara(6, 9, "image/image2.png", 12, 0, 0, map);
        hime = new HakoChara(3, 9, "image/hime.png", 13, 0, 0, map);
        fuji = new HakoChara(12, 6, "image/fuji.png", 14, 0, 0, map);
        mae = new HakoChara(9, 6, "image/mae.png", 15, 0, 0, map);
        yoshi = new HakoChara(6, 6, "image/yoshi.png", 16, 0, 0, map);
        matsuda = new HakoChara(3, 6, "image/matsu.png", 17, 0, 0, map);
        ono = new HakoChara(12, 3, "image/ono.png", 18, 0, 0, map);
        takuya = new HakoChara(9, 3, "image/matsu.png", 19, 0, 0, map);
        youko = new HakoChara(6, 3, "image/image2.png", 20, 0, 0, map);
        naga = new HakoChara(3, 3, "image/naga.png", 21, 0, 0, map);
        
        shironeko = new HakoChara(2, 2, "image/shironeko.png", 21, 3, 1, map);
        nekonin1 = new HakoChara(13, 13, "image/nekonin1.png", 21, 3, 1, map);
        sensei2 = new HakoChara(13, 6, "image/sensei2.png", 21, 3, 1, map);
        sensei3 = new HakoChara(2, 15, "image/sensei3.png", 21, 3, 1, map);

        // �}�b�v�ɃL�����N�^�[��o�^
        // �L�����N�^�[�̓}�b�v�ɑ���
        map.addHakoChara(hero);
        map.addHakoChara(sensei);
        map.addHakoChara(ara);
        map.addHakoChara(oo);
        map.addHakoChara(oshi);
        map.addHakoChara(oni);
        map.addHakoChara(kama);
        map.addHakoChara(kino);
        map.addHakoChara(kugi);
        map.addHakoChara(kubo);
        map.addHakoChara(kuro);
        map.addHakoChara(naka);
        map.addHakoChara(hashi);
        map.addHakoChara(hime);
        map.addHakoChara(fuji);
        map.addHakoChara(mae);
        map.addHakoChara(yoshi);
        map.addHakoChara(matsuda);
        map.addHakoChara(ono);
        map.addHakoChara(takuya);
        map.addHakoChara(youko);
        map.addHakoChara(naga);
        
        map.addHakoChara(shironeko);
        map.addHakoChara(nekonin1);
        map.addHakoChara(sensei2);
        map.addHakoChara(sensei3);

        // �E�B���h�E��ǉ�
        messageWindow = new MessageWindow(WND_RECT);

        // �Q�[�����[�v�J�n
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // X�����̃I�t�Z�b�g���v�Z
        int offsetX = HakoPanel.WIDTH / 2 - hero.getPx();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, HakoPanel.WIDTH - map.getWidth());

        // Y�����̃I�t�Z�b�g���v�Z
        int offsetY = HakoPanel.HEIGHT / 2 - hero.getPy();
        // �}�b�v�̒[�ł̓X�N���[�����Ȃ��悤�ɂ���
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, HakoPanel.HEIGHT - map.getHeight());

        // �}�b�v��`��
        // �L�����N�^�[�̓}�b�v���`���Ă����
        map.draw(g, offsetX, offsetY);

        // ���b�Z�[�W�E�B���h�E��`��
        messageWindow.draw(g);
    }

    public void run() {
        while (true) {
            // �L�[���͂��`�F�b�N����
            if (messageWindow.isVisible()) { // ���b�Z�[�W�E�B���h�E�\����
                messageWindowCheckInput();
            } else { // ���C�����
                mainWindowCheckInput();
            }

            if (!messageWindow.isVisible()) {
                // ��l���̈ړ�����
                heroMove();
                // �L�����N�^�[�̈ړ�����
                charaMove();
            }

            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���C���E�B���h�E�ł̃L�[���͂��`�F�b�N����
     */
    private void mainWindowCheckInput() {
        if (leftKey.isPressed()) { // ��
            if (!hero.isMoving()) { // �ړ����łȂ����
                hero.setDirection(LEFT); // �������Z�b�g����
                hero.setMoving(true); // �ړ��i�X�N���[���j�J�n
            }
        }
        if (rightKey.isPressed()) { // �E
            if (!hero.isMoving()) {
                hero.setDirection(RIGHT);
                hero.setMoving(true);
            }
        }
        if (upKey.isPressed()) { // ��
            if (!hero.isMoving()) {
                hero.setDirection(UP);
                hero.setMoving(true);
            }
        }
        if (downKey.isPressed()) { // ��
            if (!hero.isMoving()) {
                hero.setDirection(DOWN);
                hero.setMoving(true);
            }
        }
        if (spaceKey.isPressed()) {  // �X�y�[�X
            // �ړ����͕\���ł��Ȃ�
            if (hero.isMoving()) return;
            if (!messageWindow.isVisible()) {  // ���b�Z�[�W�E�B���h�E��\��
                HakoChara chara = hero.talkWith();
                if (chara != null) {
                    // ���b�Z�[�W���Z�b�g����
                    messageWindow.setMessage(chara.getMessage());
                    // ���b�Z�[�W�E�B���h�E��\��
                    messageWindow.show();
                } else {
                    messageWindow.setMessage("���̂ق������ɂ́@��������Ȃ��B");
                    messageWindow.show();
                }
            }
        }
    }


    /**
     * ���b�Z�[�W�E�B���h�E�ł̃L�[���͂��`�F�b�N����
     */
    private void messageWindowCheckInput() {
        if (spaceKey.isPressed()) {
            if (messageWindow.nextMessage()) {  // ���̃��b�Z�[�W��
                messageWindow.hide();  // �I��������B��
            }
        }
    }
    

    /**
     * ��l���̈ړ�����
     */
    private void heroMove() {
        // �ړ��i�X�N���[���j���Ȃ�ړ�����
        if (hero.isMoving()) {
            if (hero.move()) { // �ړ��i�X�N���[���j
                // �ړ�������������̏����͂����ɏ���
            }
        }
    }

    /**
     * ��l���ȊO�̃L�����N�^�[�̈ړ����� �ǂ����邩�l����
     */
    private void charaMove() {
        // �}�b�v�ɂ���L�����N�^�[���擾
        Vector charas = map.getCharas();
        for (int i = 0; i < charas.size(); i++) {
            HakoChara chara = (HakoChara) charas.get(i);
            // �L�����N�^�[�̈ړ��^�C�v�𒲂ׂ�
            if (chara.getMoveType() == 1) { // �ړ�����^�C�v�Ȃ�
                if (chara.isMoving()) { // �ړ����Ȃ�
                    chara.move(); // �ړ�����
                } else if (rand.nextDouble() < HakoChara.PROB_MOVE) {
                    // �ړ����ĂȂ��ꍇ��Chara.PROB_MOVE�̊m���ōĈړ�����
                    // �����̓����_���Ɍ��߂�
                    chara.setDirection(rand.nextInt(4));
                    chara.setMoving(true);
                }
            }
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */

    public void keyPressed(KeyEvent e) {
        // Examine the key pressed
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            leftKey.press();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKey.press();
        }
        if (keyCode == KeyEvent.VK_UP) {
            upKey.press();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downKey.press();
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spaceKey.press();
        }
    }

    /**
     * �L�[�������ꂽ��L�[�̏�Ԃ��u�����ꂽ�v�ɕς���
     * 
     * @param e �L�[�C�x���g
     */

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            leftKey.release();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKey.release();
        }
        if (keyCode == KeyEvent.VK_UP) {
            upKey.release();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            downKey.release();
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spaceKey.release();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

}