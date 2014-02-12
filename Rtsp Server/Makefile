CPPFLAGS += -I. -I$(top_builddir)/include -I/usr/include/gstreamer-0.10 -I/usr/include/glib-2.0 -I/usr/lib/arm-linux-gnueabihf/glib-2.0/include -I/usr/include/libxml2 -I/usr/lib/glib-2.0/include -I/usr/include/gstreamer-0.10/gst/rtsp-server/

OBJ = rtsptest.o

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CPPFLAGS)

rtsptest: $(OBJ)
	$(CC) -o $@ $^ $(LDFLAGS) $(CPPFLAGS) -pthread -lstdc++ -lgstreamer-0.10 -lgstrtsp-0.10 -lgstrtspserver-0.10 -lgobject-2.0 -lgmodule-2.0 -lgthread-2.0 -lrt -lxml2 -lglib-2.0 -lm

clean:
	rm *.o
	rm test
