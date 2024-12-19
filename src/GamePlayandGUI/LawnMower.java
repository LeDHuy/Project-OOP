package GamePlayandGUI;
import javax.swing.*;

import entity.zombie.Zombie;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.function.IntFunction;

public class LawnMower extends ArrayList<LawnMower> {
    protected int posX, posY, lane;
    boolean activated = false;
    protected GamePanel gp;
    public int myLane;

    // Constructor cho LawnMower
    public LawnMower(GamePanel parent, int lane) {
        this.gp = parent; // Gán game panel cho biến gp
        this.myLane = lane; // Gán lane cho biến myLane
        this.posX = 0; // Gán giá trị ban đầu của posX cho LawnMower
        this.posY = 130 + lane * 120; // Gán vị trí X cho hạt LawnMower
    }

    // Phương thức di chuyển (advance) của LawnMower
    public void advanced() {
        // Tạo hình chữ nhật đại diện cho LawnMower
        Rectangle mowerRect = new Rectangle(posX, posY, 28, 28); // Dùng kích thước và vị trí của LawnMower để tạo hình chữ nhật

        // Duyệt qua tất cả các zombie trong lane hiện tại
        for (int i = 0; i < gp.laneZombies.get(myLane).size(); i++) {
            Zombie z = gp.laneZombies.get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.posX, 109 + myLane * 120, 400, 120); // Vị trí và kích thước của zombie

            // Kiểm tra va chạm giữa LawnMower và zombie
            if (mowerRect.intersects(zRect)) {
                activated = true;
                activate(); // Kích hoạt máy cắt cỏ và truyền chỉ số zombie
                 // Thoát khỏi vòng lặp sau khi xử lý zombie
            }
        }

        // Di chuyển LawnMower sang phải mỗi lần gọi advance
        posX += 15; // Di chuyển LawnMower 15 đơn vị mỗi lần

        // Nếu LawnMower ra khỏi màn hình, loại bỏ nó khỏi game
        if (posX > 2000) {
            gp.lanePeas.get(myLane).remove(this); // Loại bỏ LawnMower khỏi danh sách của lane
        }
    }

    // Phương thức kích hoạt máy cắt cỏ
    private void activate() {
        // Thực hiện hành động kích hoạt, ví dụ như tiêu diệt tất cả zombie trong lane
        System.out.println("Lawnmower activated!");
        new Thread(() -> {
            while (posX <= 1500) { // Giới hạn di chuyển máy cắt cỏ
                posX += 1; // Di chuyển máy cắt cỏ
                try {
                    Thread.sleep(5); // Delay giữa các lần di chuyển (5ms)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        ArrayList<Zombie> zombiesInLane = gp.laneZombies.get(myLane); // Lấy danh sách zombies tại lane 0
    zombiesInLane.removeIf(zombie -> {
        // Kiểm tra nếu zombie ở vị trí (0,0)
        int zombieCol = (zombie.posX - 44) / 100;  // Tính cột của zombie
        return zombieCol == 0; // Chỉ loại bỏ zombie ở cột 0
    }); 
    }

    public boolean isActivated() {
        return activated;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
     
}

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return super.toArray(generator);
    }
}
