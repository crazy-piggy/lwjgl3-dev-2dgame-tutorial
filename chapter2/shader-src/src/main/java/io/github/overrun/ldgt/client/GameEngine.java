package io.github.overrun.ldgt.client;

/**
 * @author squid233
 * @since 2021/03/04
 */
public final class GameEngine implements Runnable {
    private final Window window;
    private final IGameLogic logic;
    private final Timer timer = new Timer();

    public GameEngine(String title, int width, int height, IGameLogic logic) {
        window = new Window(title, width, height);
        this.logic = logic;
    }

    public void init() {
        window.init();
        timer.init();
        logic.init();
    }

    public void input() {
        logic.input(window);
    }

    public void update(float delta) {
        logic.update(delta);
    }

    public void render() {
        logic.render(window);
        window.update();
    }

    private void loop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / 30;
        while (!window.shouldClose()) {
            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;
            input();
            while (accumulator >= interval) {
                update(interval);
                accumulator -= interval;
            }
            render();
        }
    }

    @Override
    public void run() {
        init();
        loop();
    }
}
