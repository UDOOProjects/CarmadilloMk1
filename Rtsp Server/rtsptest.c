#include <gst/gst.h>
#include <gst/rtsp-server/rtsp-server.h>

int main (int argc, char *argv[])
{
  GMainLoop *loop;
  GstRTSPServer *server;
  GstRTSPMediaMapping *mapping;
  GstRTSPMediaFactory *factory;

  gst_init (&argc, &argv);
  loop = g_main_loop_new (NULL, FALSE);
  server = gst_rtsp_server_new ();
  mapping = gst_rtsp_server_get_media_mapping (server);
  factory = gst_rtsp_media_factory_new ();
  gst_rtsp_media_factory_set_launch (factory,
       "( mfw_v4lsrc capture-mode=0 is-live=1 ! vpuenc codec=0 ! rtpmp4vpay send-config=true name=pay0 pt=96 )");

  
  gst_rtsp_media_factory_set_shared (factory, TRUE);
  gst_rtsp_media_mapping_add_factory (mapping, "/test", factory);
  g_object_unref (mapping);
  gst_rtsp_server_attach (server, NULL);
  g_main_loop_run (loop);

  return 0;
}
