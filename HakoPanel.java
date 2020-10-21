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
    // パネルサイズ
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    private HakoMap map; // マップ
    private HakoChara hero; // 主人公

    private HakoChara sensei; //先生
    private HakoChara ara; //生徒
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

    // アクションキー
    private HakoActionKey leftKey;
    private HakoActionKey rightKey;
    private HakoActionKey upKey;
    private HakoActionKey downKey;
    private HakoActionKey spaceKey;

    // ゲームループ開始
    private Thread gameLoop;

    // 乱数生成器
    private Random rand = new Random();

    // ウィンドウ
    private MessageWindow messageWindow;
    // ウィンドウを表示する領域
    private static Rectangle WND_RECT = new Rectangle(62, 324, 356, 140);

    public HakoPanel() {

        // パネルの推奨サイズを設定
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // パネルがキー操作を受け付けるように登録する
        setFocusable(true);
        addKeyListener(this);

        // アクションキーを作成
        leftKey = new HakoActionKey();
        rightKey = new HakoActionKey();
        upKey = new HakoActionKey();
        downKey = new HakoActionKey();
        spaceKey = new HakoActionKey(HakoActionKey.DETECT_INITIAL_PRESS_ONLY);

        // マップ、主人公、生徒の作成(ファイルの読み込み)
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

        // マップにキャラクターを登録
        // キャラクターはマップに属す
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

        // ウィンドウを追加
        messageWindow = new MessageWindow(WND_RECT);

        // ゲームループ開始
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // X方向のオフセットを計算
        int offsetX = HakoPanel.WIDTH / 2 - hero.getPx();
        // マップの端ではスクロールしないようにする
        offsetX = Math.min(offsetX, 0);
        offsetX = Math.max(offsetX, HakoPanel.WIDTH - map.getWidth());

        // Y方向のオフセットを計算
        int offsetY = HakoPanel.HEIGHT / 2 - hero.getPy();
        // マップの端ではスクロールしないようにする
        offsetY = Math.min(offsetY, 0);
        offsetY = Math.max(offsetY, HakoPanel.HEIGHT - map.getHeight());

        // マップを描く
        // キャラクターはマップが描いてくれる
        map.draw(g, offsetX, offsetY);

        // メッセージウィンドウを描画
        messageWindow.draw(g);
    }

    public void run() {
        while (true) {
            // キー入力をチェックする
            if (messageWindow.isVisible()) { // メッセージウィンドウ表示中
                messageWindowCheckInput();
            } else { // メイン画面
                mainWindowCheckInput();
            }

            if (!messageWindow.isVisible()) {
                // 主人公の移動処理
                heroMove();
                // キャラクターの移動処理
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
     * メインウィンドウでのキー入力をチェックする
     */
    private void mainWindowCheckInput() {
        if (leftKey.isPressed()) { // 左
            if (!hero.isMoving()) { // 移動中でなければ
                hero.setDirection(LEFT); // 方向をセットして
                hero.setMoving(true); // 移動（スクロール）開始
            }
        }
        if (rightKey.isPressed()) { // 右
            if (!hero.isMoving()) {
                hero.setDirection(RIGHT);
                hero.setMoving(true);
            }
        }
        if (upKey.isPressed()) { // 上
            if (!hero.isMoving()) {
                hero.setDirection(UP);
                hero.setMoving(true);
            }
        }
        if (downKey.isPressed()) { // 下
            if (!hero.isMoving()) {
                hero.setDirection(DOWN);
                hero.setMoving(true);
            }
        }
        if (spaceKey.isPressed()) {  // スペース
            // 移動中は表示できない
            if (hero.isMoving()) return;
            if (!messageWindow.isVisible()) {  // メッセージウィンドウを表示
                HakoChara chara = hero.talkWith();
                if (chara != null) {
                    // メッセージをセットする
                    messageWindow.setMessage(chara.getMessage());
                    // メッセージウィンドウを表示
                    messageWindow.show();
                } else {
                    messageWindow.setMessage("そのほうこうには　だれもいない。");
                    messageWindow.show();
                }
            }
        }
    }


    /**
     * メッセージウィンドウでのキー入力をチェックする
     */
    private void messageWindowCheckInput() {
        if (spaceKey.isPressed()) {
            if (messageWindow.nextMessage()) {  // 次のメッセージへ
                messageWindow.hide();  // 終了したら隠す
            }
        }
    }
    

    /**
     * 主人公の移動処理
     */
    private void heroMove() {
        // 移動（スクロール）中なら移動する
        if (hero.isMoving()) {
            if (hero.move()) { // 移動（スクロール）
                // 移動が完了した後の処理はここに書く
            }
        }
    }

    /**
     * 主人公以外のキャラクターの移動処理 どうするか考える
     */
    private void charaMove() {
        // マップにいるキャラクターを取得
        Vector charas = map.getCharas();
        for (int i = 0; i < charas.size(); i++) {
            HakoChara chara = (HakoChara) charas.get(i);
            // キャラクターの移動タイプを調べる
            if (chara.getMoveType() == 1) { // 移動するタイプなら
                if (chara.isMoving()) { // 移動中なら
                    chara.move(); // 移動する
                } else if (rand.nextDouble() < HakoChara.PROB_MOVE) {
                    // 移動してない場合はChara.PROB_MOVEの確率で再移動する
                    // 方向はランダムに決める
                    chara.setDirection(rand.nextInt(4));
                    chara.setMoving(true);
                }
            }
        }
    }

    /**
     * キーが押されたらキーの状態を「押された」に変える
     * 
     * @param e キーイベント
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
     * キーが離されたらキーの状態を「離された」に変える
     * 
     * @param e キーイベント
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