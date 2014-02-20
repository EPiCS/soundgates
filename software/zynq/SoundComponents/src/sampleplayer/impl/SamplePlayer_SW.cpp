/*
 * SamplePlayerSW.cpp
 *
 *  Created on: Dec 28, 2013
 *      Author: lukas
 */

#include "SamplePlayer_SW.h"

SamplePlayer_SW::SamplePlayer_SW(std::vector<std::string> params) : SamplePlayer(params) {

    m_StringToLoadPolicyMap["LOAD_ON_DEMAND"]     = LOAD_ON_DEMAND;
    m_StringToLoadPolicyMap["PRELOAD"]            = PRELOAD;
    m_StringToLoadPolicyMap["PRELOAD_DECOMPRESS"] = PRELOAD_DECOMPRESS;

    m_LoadingPolicy = PRELOAD_DECOMPRESS;  /* Set preload as default loading policy */
    m_Playbackstate = STOP;
    m_CompressedDataBuffer   = NULL;
    m_DecompressedDataBuffer = NULL;

    m_CurrentPlaybackOffset  = 0;

    m_DecompressedDataSize   = 0;

    /* First parameter should be the filename */
    if (params.size() > 0) {
        m_SourceFile = params[0];

        m_Codec         = NULL;
        m_Decoded_Frame = NULL;
        m_CodecCtx      = NULL;
        m_ContainerCtx  = NULL;

        if(params.size() > 1){
            std::string policyStr = params[1];

            std::transform(policyStr.begin(), policyStr.end(), policyStr.begin(), ::toupper);

            std::map<std::string, eLoadPolicy>::iterator iter = m_StringToLoadPolicyMap.find(params[1]);

            if(iter != m_StringToLoadPolicyMap.end()){
                m_LoadingPolicy = iter->second;
            }else{
               LOG_WARNING("Unknown loading policy: " << policyStr);
            }
        }
    }
}

SamplePlayer_SW::~SamplePlayer_SW() {

    avcodec_close(m_CodecCtx);
}

void SamplePlayer_SW::initCodec() {

    avcodec_register_all();
    av_register_all();
    AVDictionary* dict;

    int stream_index = -1;

    if (avformat_open_input(&m_ContainerCtx, m_SourceFile.c_str(), NULL, NULL) < 0) {
            LOG_ERROR("Cound not open file: " << m_SourceFile.c_str());
    }

    avformat_find_stream_info(m_ContainerCtx, NULL);

    LOG_INFO("Loading audio file " << m_SourceFile);
    LOG_INFO("#Streams: " << m_ContainerCtx->nb_streams);

    for (unsigned int i = 0; i < m_ContainerCtx->nb_streams; i++) {
        if (m_ContainerCtx->streams[i]->codec->codec_type == AVMEDIA_TYPE_AUDIO) {
            stream_index = i;
            break;
        }
    }

    if(stream_index == -1){
        LOG_ERROR("Could not find audio");
    }

    m_CodecCtx = m_ContainerCtx->streams[stream_index]->codec;

    m_Codec    = avcodec_find_decoder(m_CodecCtx->codec_id);

    if (!m_Codec) {

        LOG_ERROR("Codec not found");
        avformat_close_input(&m_ContainerCtx);
        exit(1);
    }
    /* request internal sample format */

    LOG_INFO("Codec tag: "<< m_Codec->name << ", Codec ID: " << m_Codec->id);

    LOG_DEBUG("#Channels: " << m_CodecCtx->channels);

    /* open it */
    if (avcodec_open2(m_CodecCtx, m_Codec, NULL) < 0) {
        LOG_ERROR("Could not open codec");
        exit(1);
    }
}

void SamplePlayer_SW::init() {



    AVPacket avpkt;
    ReSampleContext* resmplCtx;
    int compressed_bytes_consumed    = 0;
    int compressed_file_size         = 0;
    uint8_t* l_ResampleBuffer;

    initCodec();                /* Initialize audio codec   */
    av_init_packet(&avpkt);     /* Initialize packet        */
//    resmplCtx = av_audio_resample_init(m_CodecCtx->channels,
//                                        m_CodecCtx->channels,
//                                        Synthesizer::config::samplerate,
//                                        m_CodecCtx->sample_rate,
//                                        AV_SAMPLE_FMT_S32,
//                                        m_CodecCtx->sample_fmt,
//                                        16, 10, 0, 1.0);

    m_DecompressedDataBuffer = new uint8_t[AVCODEC_MAX_AUDIO_FRAME_SIZE * MAX_PLAYBACK_LENGTH_SEC];
    memset(m_DecompressedDataBuffer, 0 ,AVCODEC_MAX_AUDIO_FRAME_SIZE * MAX_PLAYBACK_LENGTH_SEC);

    LOG_DEBUG("Decoding audio file");
    LOG_DEBUG("Sample format:" << av_get_sample_fmt_name(m_CodecCtx->sample_fmt));

    while (!av_read_frame(m_ContainerCtx, &avpkt)) {

        int got_frame = 0;

        if (!m_Decoded_Frame) {

            if (!(m_Decoded_Frame = avcodec_alloc_frame())) { /* allocate once */

                LOG_ERROR("Could not allocate audio frame");
                exit(1);
            }
        }else{
            avcodec_get_frame_defaults(m_Decoded_Frame);
        }

        compressed_bytes_consumed = avcodec_decode_audio4(m_CodecCtx, m_Decoded_Frame, &got_frame, &avpkt);

        if (compressed_bytes_consumed < 0) {
            LOG_ERROR("Error while decoding audio frame");
            exit(1);
        }

        if (got_frame) {
            int samples_written = 0;
            int data_size = 0;
            size_t resampled_Bytes = 0;

            /* if a frame has been decoded, output it */
            data_size = av_samples_get_buffer_size(NULL, m_CodecCtx->channels, m_Decoded_Frame->nb_samples, m_CodecCtx->sample_fmt, 1);

            int32_t* buf  = (int32_t*) (m_DecompressedDataBuffer + m_DecompressedDataSize);

            switch(m_CodecCtx->sample_fmt){
            case AV_SAMPLE_FMT_NONE:
            case AV_SAMPLE_FMT_U8:
//                for(int i = 0; i <  m_Decoded_Frame->nb_samples * m_CodecCtx->channels; i++){
//                    buf[i] = (int32_t)( ((int8_t*)  m_Decoded_Frame->data[0])[i] << 24 );
//                }
//                break;

            //FIXME: convert other sample formats
            case AV_SAMPLE_FMT_S16:
                for(int i = 0; i <  m_Decoded_Frame->nb_samples * m_CodecCtx->channels; i++){
                    buf[i] = *(((short*)m_Decoded_Frame->data[0]) + i) << 16;
                }
                m_DecompressedDataSize += m_Decoded_Frame->nb_samples * m_CodecCtx->channels * av_get_bytes_per_sample(AV_SAMPLE_FMT_S32);
                break;

            case AV_SAMPLE_FMT_S32:

                memcpy(m_DecompressedDataBuffer + m_DecompressedDataSize, m_Decoded_Frame->data[0], data_size);

                m_DecompressedDataSize += data_size;

                break;

            case AV_SAMPLE_FMT_FLT:
            case AV_SAMPLE_FMT_DBL:

            case AV_SAMPLE_FMT_U8P:
            case AV_SAMPLE_FMT_S16P:
            case AV_SAMPLE_FMT_S32P:
            case AV_SAMPLE_FMT_FLTP:
            case AV_SAMPLE_FMT_DBLP:

            default:
                LOG_ERROR("Sample format is not supported");
            }

        }
    }


    char* writeLeftPortPtr  = m_SoundOut_Left_1_Port->getWriteBuffer();
//    char* writeRightPortPtr = m_SoundOut_Right_2_Port->getWriteBuffer();

    memset(writeLeftPortPtr,  0, Synthesizer::config::bytesPerBlock);
//    memset(writeRightPortPtr, 0, Synthesizer::config::bytesPerBlock);

    m_Trigger_1_Port->registerCallback(ICallbackPtr(new OnValueChange<int, ControlPortPtr>(m_DoPlayback, m_Trigger_1_Port)));
//   audio_resample_close(resmplCtx);
//   av_free(m_CodecCtx);
//   av_free(m_Decoded_Frame);
//  avformat_free_context(m_ContainerCtx);
}

void SamplePlayer_SW::process() {

    int* writeLeftPortPtr  = (int*)m_SoundOut_Left_1_Port->getWriteBuffer();
//    int* writeRightPortPtr = (int*)m_SoundOut_Right_2_Port->getWriteBuffer();

    //int trigger = (int) m_Trigger_1_Port->pop();

    if(m_DoPlayback || (m_CurrentPlaybackOffset > 0 && m_CurrentPlaybackOffset < m_DecompressedDataSize)){

    switch(m_CodecCtx->channels){

        case 1:
            /*
             * Copy samples to left and right channel
             */
            memcpy(writeLeftPortPtr,  m_DecompressedDataBuffer + m_CurrentPlaybackOffset, Synthesizer::config::bytesPerBlock);
//            memcpy(writeRightPortPtr, m_DecompressedDataBuffer + m_CurrentPlaybackOffset, Synthesizer::config::bytesPerBlock);

            m_CurrentPlaybackOffset += Synthesizer::config::bytesPerBlock;
            //TODO: Add padding zeros when playbackoffset > n* blocksize
            break;
        case 2:
            /*
             * Copy interleaved audio samples to left and right channel
             */
            for(int i = 0; i < Synthesizer::config::blocksize; i++){

                writeLeftPortPtr[i]  = *((int*)(m_DecompressedDataBuffer + m_CurrentPlaybackOffset));
 //               writeRightPortPtr[i] = *((int*)(m_DecompressedDataBuffer + m_CurrentPlaybackOffset + Synthesizer::config::bytesPerSample));

                m_CurrentPlaybackOffset += Synthesizer::config::bytesPerSample * 2;

                if(m_CurrentPlaybackOffset >= m_DecompressedDataSize){

                    /* pad with zero */
                    for(int j = i; j < Synthesizer::config::blocksize; j++){
                        writeLeftPortPtr[j]  = 0;
//                        writeRightPortPtr[j] = 0;
                    }
                    break;
                }
            }

            break;
        default:
            LOG_ERROR("Unsupported number of channels: " << m_CodecCtx->channels);
        }

        if(m_CurrentPlaybackOffset >= m_DecompressedDataSize){
            m_CurrentPlaybackOffset = 0;
            m_DoPlayback = 0;
        }

    }else{

         memset(writeLeftPortPtr,  0, Synthesizer::config::bytesPerBlock);
//         memset(writeRightPortPtr, 0, Synthesizer::config::bytesPerBlock);
         m_CurrentPlaybackOffset = 0;
         m_DoPlayback = 0;
     }
}
