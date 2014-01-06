/*
 * SamplePlayerSW.h
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#ifndef SAMPLEPLAYERSW_H_
#define SAMPLEPLAYERSW_H_

#include "../SamplePlayer.h"

#include <cstdio>
#include <iostream>
#include <fstream>
#include <map>

extern "C" {
#include <libavcodec/avcodec.h>
#include <libavutil/samplefmt.h>
#include <libavformat/avformat.h>
}

#define AUDIO_INBUF_DEFAULT_SIZE                20480
#define AUDIO_REFILL_THRESH                     4096
#define MAX_PLAYBACK_LENGTH_SEC                 10        /*< Max supported playback length */


class SamplePlayer_SW : public SamplePlayer{
private:

    enum eLoadPolicy{
        LOAD_ON_DEMAND,     /* Load and decompress on demand */
        PRELOAD,            /* Preload file content but decompress on demand */
        PRELOAD_DECOMPRESS  /* Decompress on initialization*/
    };


    enum ePlaybackState{

        PLAY,
        STOP,
    };

    std::map<std::string, eLoadPolicy>    m_StringToLoadPolicyMap;
    ePlaybackState      m_Playbackstate;
    eLoadPolicy         m_LoadingPolicy;

    std::string         m_SourceFile;

    uint8_t*            m_CompressedDataBuffer;
    uint8_t*            m_DecompressedDataBuffer;

    unsigned int        m_CurrentPlaybackOffset;
    unsigned int        m_DecompressedDataSize;

    AVCodec*            m_Codec;
    AVCodecContext*     m_CodecCtx;
    AVFrame*            m_Decoded_Frame;
    AVFormatContext*    m_ContainerCtx;

    void initCodec();

public:
    SamplePlayer_SW(std::vector<std::string> params);
    virtual ~SamplePlayer_SW();

    void init(void);
    void process(void);

};

#endif /* SAMPLEPLAYERSW_H_ */
