package dev.bryth.ariane.events;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class LinesRenderer {
    static boolean isTracking = false;
    static boolean isRendering = true;
    static ArrayList<Vector3d> pos = new ArrayList<>();

    public static void addPosition(double x, double y, double z) {
        Vector3d point = new Vector3d();
        point.x = x; point.y = y + .5; point.z = z;

        Vector3d last = null;
        if (!pos.isEmpty()) {
            last = pos.get(pos.size() - 1);
        }

        if (last == null) {
            pos.add(point);
        }
        else {
            double dx = last.x - x;
            double dy = last.y - y;
            double dz = last.z - z;

            double distSquared = dx*dx + dy*dy + dz*dz;
            if (distSquared > 0.4) {
                pos.add(point);
            }
        }
    }


    @SubscribeEvent
    public void worldRender(RenderWorldLastEvent event)  {
        if (!isRendering) return;

        Minecraft mc = Minecraft.getMinecraft();
        double renderPosX = mc.getRenderManager().viewerPosX;
        double renderPosY = mc.getRenderManager().viewerPosY;
        double renderPosZ = mc.getRenderManager().viewerPosZ;

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glLineWidth(1f);

        GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);

        GL11.glBegin(GL11.GL_LINES);

        int size = pos.size();
        for(int i=0; i < size-2; i++) {
            Vector3d start = pos.get(i);
            Vector3d end = pos.get(i+1);

            if (start == null || end == null) continue;

            GL11.glVertex3d(start.x, start.y, start.z);
            GL11.glVertex3d(end.x, end.y, end.z);
        }

        GL11.glEnd();

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }
}