import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;

public class HakoMap implements Common {
    // �}�b�v
    private int[][] map;

    // �}�b�v�̍s���E�񐔁i�P�ʁF�}�X�j
    private int row;
    private int col;

    // �}�b�v�S�̂̑傫���i�P�ʁF�s�N�Z���j
    private int width;
    private int height;

    // Chipset
    private Image floorImage;
    private Image wallImage;
    private Image throneImage;
    private Image tobiraImage;

    // ���̃}�b�v�ɂ���L�����N�^�[����
    private Vector charas = new Vector();

    // ���C���p�l���ւ̎Q��
    private HakoPanel panel;

    public HakoMap(String filename, HakoPanel panel) {
        // �}�b�v�����[�h
        load(filename);

        // �C�x���g�����[�h
        // loadEvent(eventFile);

        // Load image
        loadImage();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // �I�t�Z�b�g�����ɕ`��͈͂����߂�
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(HakoPanel.WIDTH) + 1;
        // �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileX = Math.min(lastTileX, col);

        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(HakoPanel.HEIGHT) + 1;
        // �`��͈͂��}�b�v�̑傫�����傫���Ȃ�Ȃ��悤�ɒ���
        lastTileY = Math.min(lastTileY, row);

        for (int i = firstTileY; i < lastTileY; i++) {
            for (int j = firstTileX; j < lastTileX; j++) {
                // map�̒l�ɉ����ĉ摜��`��
                switch (map[i][j]) {
                    case 0: // floor
                        g.drawImage(floorImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 1: // wall
                        g.drawImage(wallImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 2: // �ʍ�
                        g.drawImage(throneImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 3: // ��
                        g.drawImage(tobiraImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                }
            }
        }

        // ���̃}�b�v�ɂ���L�����N�^�[��`��
        for (int n = 0; n < charas.size(); n++) {
            HakoChara chara = (HakoChara) charas.get(n);
            chara.draw(g, offsetX, offsetY);
        }

    }

    /**
     * (x,y)�ɂԂ�����̂����邩���ׂ�B
     * 
     * @param x �}�b�v��x���W
     * @param y �}�b�v��y���W
     * @return (x,y)�ɂԂ�����̂���������true��Ԃ��B
     */

    public boolean isHit(int x, int y) {
        // (x,y)�ɕǂ��ʍ�����������Ԃ���
        // wall = 1
        if (map[y][x] == 1 || map[y][x] == 2) {
            return true;
        }

        // ���̃L�����N�^�[��������Ԃ���
        for (int i = 0; i < charas.size(); i++) {
            HakoChara chara = (HakoChara) charas.get(i);
            if (chara.getX() == x && chara.getY() == y) {
                return true;
            }
        }

        // �Ȃ���΂Ԃ���Ȃ�
        return false;
    }

    /**
     * �L�����N�^�[�����̃}�b�v�ɒǉ�����
     * 
     * @param chara �L�����N�^�[
     */
    public void addHakoChara(HakoChara chara) {
        charas.add(chara);
    }

    public HakoChara charaCheck(int x, int y) {
        for (int i = 0; i < charas.size(); i++) {
            HakoChara chara = (HakoChara) charas.get(i);
            if (chara.getX() == x && chara.getY() == y) {
                return chara;
            }
        }

        return null;
    }

    /**
     * �s�N�Z���P�ʂ��}�X�P�ʂɕύX����
     * 
     * @param pixels �s�N�Z���P��
     * @return �}�X�P��
     */
    public static int pixelsToTiles(double pixels) {
        return (int) Math.floor(pixels / CS);
    }

    /**
     * �}�X�P�ʂ��s�N�Z���P�ʂɕύX����
     * 
     * @param tiles �}�X�P��
     * @return �s�N�Z���P��
     */
    public static int tilesToPixels(int tiles) {
        return tiles * CS;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector getCharas() {
        return charas;
    }

    /**
     * �t�@�C������}�b�v��ǂݍ���
     * 
     * @param filename �ǂݍ��ރ}�b�v�f�[�^�̃t�@�C����
     */

    private void load(String filename) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            // row��ǂݍ���
            String line = br.readLine();
            row = Integer.parseInt(line);
            // col��ǂ�
            line = br.readLine();
            col = Integer.parseInt(line);
            // �}�b�v�T�C�Y��ݒ�
            width = col * CS;
            height = row * CS;
            // �}�b�v���쐬
            map = new int[row][col];
            for (int i = 0; i < row; i++) {
                line = br.readLine();
                for (int j = 0; j < col; j++) {
                    map[i][j] = Integer.parseInt(line.charAt(j) + "");
                }
            }
            // show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �C�x���g�����[�h����
     * 
     * @param filename �C�x���g�t�@�C��
     */
    // private void loadEvent(String filename) {
    //     try {
    //         BufferedReader br = new BufferedReader(
    //                 new InputStreamReader(getClass().getResourceAsStream(filename), "Shift_JIS"));
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             // ��s�͓ǂݔ�΂�
    //             if (line.equals(""))
    //                 continue;
    //             // �R�����g�s�͓ǂݔ�΂�
    //             if (line.startsWith("#"))
    //                 continue;
    //             StringTokenizer st = new StringTokenizer(line, ",");
    //             // �C�x���g�����擾����
    //             // �C�x���g�^�C�v���擾���ăC�x���g���Ƃɏ�������
    //             String eventType = st.nextToken();
    //             if (eventType.equals("CHARA")) { // �L�����N�^�[�C�x���g
    //                 makeCharacter(st);
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * �C���[�W�����[�h �����Œǉ������ԍ������܂�
     */

    private void loadImage() {

        ImageIcon icon = new ImageIcon(getClass().getResource("image/floor.gif"));
        floorImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("image/wall.gif"));
        wallImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("image/desk.png"));
        throneImage = icon.getImage();

        icon = new ImageIcon(getClass().getResource("image/tobira.png"));
        tobiraImage = icon.getImage();

        
    }

    /**
     * �L�����N�^�[�C�x���g���쐬
     */
    // private void makeCharacter(StringTokenizer st) {
    //     // �C�x���g�̍��W
    //     int x = Integer.parseInt(st.nextToken());
    //     int y = Integer.parseInt(st.nextToken());
    //     // �L�����N�^�ԍ�
    //     int charaNo = Integer.parseInt(st.nextToken());
    //     // ����
    //     int dir = Integer.parseInt(st.nextToken());
    //     // �ړ��^�C�v
    //     int moveType = Integer.parseInt(st.nextToken());
    //     // ���b�Z�[�W
    //     String message = st.nextToken();
    //     // �L�����N�^�[���쐬
    //     HakoChara c = new HakoChara(x, y, filename, charaNo, dir, moveType, this);
    //     // ���b�Z�[�W��o�^
    //     c.setMessage(message);
    //     // �L�����N�^�[�x�N�g���ɓo�^
    //     charas.add(c);
    // }

    /**
     * �}�b�v���R���\�[���ɕ\���B�f�o�b�O�p�B
     */
    public void show() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
