import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;

public class HakoMap implements Common {
    // マップ
    private int[][] map;

    // マップの行数・列数（単位：マス）
    private int row;
    private int col;

    // マップ全体の大きさ（単位：ピクセル）
    private int width;
    private int height;

    // Chipset
    private Image floorImage;
    private Image wallImage;
    private Image throneImage;
    private Image tobiraImage;

    // このマップにいるキャラクターたち
    private Vector charas = new Vector();

    // メインパネルへの参照
    private HakoPanel panel;

    public HakoMap(String filename, HakoPanel panel) {
        // マップをロード
        load(filename);

        // イベントをロード
        // loadEvent(eventFile);

        // Load image
        loadImage();
    }

    public void draw(Graphics g, int offsetX, int offsetY) {
        // オフセットを元に描画範囲を求める
        int firstTileX = pixelsToTiles(-offsetX);
        int lastTileX = firstTileX + pixelsToTiles(HakoPanel.WIDTH) + 1;
        // 描画範囲がマップの大きさより大きくならないように調整
        lastTileX = Math.min(lastTileX, col);

        int firstTileY = pixelsToTiles(-offsetY);
        int lastTileY = firstTileY + pixelsToTiles(HakoPanel.HEIGHT) + 1;
        // 描画範囲がマップの大きさより大きくならないように調整
        lastTileY = Math.min(lastTileY, row);

        for (int i = firstTileY; i < lastTileY; i++) {
            for (int j = firstTileX; j < lastTileX; j++) {
                // mapの値に応じて画像を描く
                switch (map[i][j]) {
                    case 0: // floor
                        g.drawImage(floorImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 1: // wall
                        g.drawImage(wallImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 2: // 玉座
                        g.drawImage(throneImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                    case 3: // 扉
                        g.drawImage(tobiraImage, tilesToPixels(j) + offsetX, tilesToPixels(i) + offsetY, panel);
                        break;
                }
            }
        }

        // このマップにいるキャラクターを描画
        for (int n = 0; n < charas.size(); n++) {
            HakoChara chara = (HakoChara) charas.get(n);
            chara.draw(g, offsetX, offsetY);
        }

    }

    /**
     * (x,y)にぶつかるものがあるか調べる。
     * 
     * @param x マップのx座標
     * @param y マップのy座標
     * @return (x,y)にぶつかるものがあったらtrueを返す。
     */

    public boolean isHit(int x, int y) {
        // (x,y)に壁か玉座があったらぶつかる
        // wall = 1
        if (map[y][x] == 1 || map[y][x] == 2) {
            return true;
        }

        // 他のキャラクターがいたらぶつかる
        for (int i = 0; i < charas.size(); i++) {
            HakoChara chara = (HakoChara) charas.get(i);
            if (chara.getX() == x && chara.getY() == y) {
                return true;
            }
        }

        // なければぶつからない
        return false;
    }

    /**
     * キャラクターをこのマップに追加する
     * 
     * @param chara キャラクター
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
     * ピクセル単位をマス単位に変更する
     * 
     * @param pixels ピクセル単位
     * @return マス単位
     */
    public static int pixelsToTiles(double pixels) {
        return (int) Math.floor(pixels / CS);
    }

    /**
     * マス単位をピクセル単位に変更する
     * 
     * @param tiles マス単位
     * @return ピクセル単位
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
     * ファイルからマップを読み込む
     * 
     * @param filename 読み込むマップデータのファイル名
     */

    private void load(String filename) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            // rowを読み込む
            String line = br.readLine();
            row = Integer.parseInt(line);
            // colを読む
            line = br.readLine();
            col = Integer.parseInt(line);
            // マップサイズを設定
            width = col * CS;
            height = row * CS;
            // マップを作成
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
     * イベントをロードする
     * 
     * @param filename イベントファイル
     */
    // private void loadEvent(String filename) {
    //     try {
    //         BufferedReader br = new BufferedReader(
    //                 new InputStreamReader(getClass().getResourceAsStream(filename), "Shift_JIS"));
    //         String line;
    //         while ((line = br.readLine()) != null) {
    //             // 空行は読み飛ばす
    //             if (line.equals(""))
    //                 continue;
    //             // コメント行は読み飛ばす
    //             if (line.startsWith("#"))
    //                 continue;
    //             StringTokenizer st = new StringTokenizer(line, ",");
    //             // イベント情報を取得する
    //             // イベントタイプを取得してイベントごとに処理する
    //             String eventType = st.nextToken();
    //             if (eventType.equals("CHARA")) { // キャラクターイベント
    //                 makeCharacter(st);
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    /**
     * イメージをロード ここで追加した番号が決まる
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
     * キャラクターイベントを作成
     */
    // private void makeCharacter(StringTokenizer st) {
    //     // イベントの座標
    //     int x = Integer.parseInt(st.nextToken());
    //     int y = Integer.parseInt(st.nextToken());
    //     // キャラクタ番号
    //     int charaNo = Integer.parseInt(st.nextToken());
    //     // 向き
    //     int dir = Integer.parseInt(st.nextToken());
    //     // 移動タイプ
    //     int moveType = Integer.parseInt(st.nextToken());
    //     // メッセージ
    //     String message = st.nextToken();
    //     // キャラクターを作成
    //     HakoChara c = new HakoChara(x, y, filename, charaNo, dir, moveType, this);
    //     // メッセージを登録
    //     c.setMessage(message);
    //     // キャラクターベクトルに登録
    //     charas.add(c);
    // }

    /**
     * マップをコンソールに表示。デバッグ用。
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
