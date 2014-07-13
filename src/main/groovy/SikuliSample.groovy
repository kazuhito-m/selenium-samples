import org.sikuli.api.DesktopScreenRegion
import org.sikuli.api.ImageTarget
import org.sikuli.api.ScreenRegion
import org.sikuli.api.Target
import org.sikuli.api.robot.Mouse
import org.sikuli.api.robot.desktop.DesktopMouse
import org.sikuli.api.visual.Canvas
import org.sikuli.api.visual.DesktopCanvas

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static org.sikuli.api.API.browse

class SikuliSample {

    void execSikuli() {

        // Open the main page of Google Code in the default web browser
        browse(new URL("http://code.google.com"));

        // Create a screen region object that corresponds to the default monitor in full screen
        ScreenRegion s = new DesktopScreenRegion();

        // Specify an image as the target to find on the screen
        URL imageURL = new URL("http://code.google.com/images/code_logo.gif");
        Target imageTarget = new ImageTarget(imageURL);

        // Wait for the target to become visible on the screen for at most 5 seconds
        // Once the target is visible, it returns a screen region object corresponding
        // to the region occupied by this target
        ScreenRegion r = s.wait(imageTarget, 5000);

        // Display "Hello World" next to the found target for 3 seconds
        Canvas canvas = newmain DesktopCanvas();
        canvas.addLabel(r, "Hello World").display(3);

        // Click the center of the found target
        Mouse mouse = new DesktopMouse();
        mouse.click(r.getCenter());
    }

    void watchEvo2014OnNiconico(String url) {

        // 予め「画面探索用の画像」を読んで置く。
        def images = [:]
        ["peke","wide","premia_w","premia_n","update"].each { images[it] = new ImageTarget(ImageIO.read(new File("out/production/selenium-samples/${it}.png"))) }

        ScreenRegion s = new DesktopScreenRegion()
        Mouse mouse = new DesktopMouse();

        def niconamaScreenInitialize = {
            // ペケマークを探してクリック
            ScreenRegion r = s.wait(images.peke, 5000)
            mouse.click(r.center)
            // 全画面表示を探してクリック
            r = s.wait(images.wide, 5000)
            mouse.click(r.center)
        }

        // ブラウザ開く
        browse(new URL(url))
        // スクリーン初期化
        niconamaScreenInitialize()

        (1..3600).each {
            Thread.sleep(3000)
            // 「プレミアの方が入場されました」をキャッチしたら、すかさずブラウザを更新。
            if (s.wait(images.premia_w,500) != null || s.wait(images.premia_n,500) != null) {
                // ブラウザの更新アイコンをクリック
                ScreenRegion r = s.wait(images.update, 5000)
                mouse.click(r.center)
                // スクリーン初期化
                niconamaScreenInitialize()
            }
            println "${it} - Not found Oidashi messages. : " + new Date().toLocaleString()
        }
    }

}
