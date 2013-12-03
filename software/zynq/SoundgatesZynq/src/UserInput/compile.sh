gcc -I../include -c -o tcp_handshake tcp_handshake.c
gcc -I../include -c -o osc_handler osc_handler.c
gcc -I../include -c -o user_input user_input.c
gcc tcp_handshake osc_handler user_input -Wl,-rpath,/usr/local/lib -llo -lpthread -o ui
rm tcp_handshake osc_handler user_input
