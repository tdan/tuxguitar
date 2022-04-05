#ifndef _Included_LV2Client
#define _Included_LV2Client

#include "LV2.h"
#include "LV2Socket.h"

typedef struct {
	pthread_mutex_t lock;
	LV2Int32 sessionId;
	LV2Int32 serverPort;
	LV2Int32 bufferSize;
	const char *pluginURI;
	
	LV2World *world;
	LV2Plugin *plugin;
	LV2Feature *feature;
	LV2Instance *instance;
	LV2UI *ui;
	LV2Socket *socket;

	float** inputsBuffer;
	float** outputsBuffer;
} LV2Client;

int main(int argc, char *argv[]);

void LV2Client_parseArguments(LV2Client *handle, int argc , char *argv[]);

void LV2Client_createBuffers(LV2Client *handle);

void LV2Client_destroyBuffers(LV2Client *handle);

void LV2Client_processCommand(LV2Client *handle, int command);

void LV2Client_processGetControlPortValueCommand(LV2Client *handle);

void LV2Client_processSetControlPortValueCommand(LV2Client *handle);

void LV2Client_processProcessMidiMessagesCommand(LV2Client *handle);

void LV2Client_processProcessAudioCommand(LV2Client *handle);

void LV2Client_processOpenUICommand(LV2Client *handle);

void LV2Client_processCloseUICommand(LV2Client *handle);

void LV2Client_processUIIsOpenCommand(LV2Client *handle);

void LV2Client_processUIIsAvailableCommand(LV2Client *handle);

void LV2Client_processUIIsUpdatedCommand(LV2Client *handle);

void* LV2Client_processCommandsThread(void* ptr);

#endif /* _Included_LV2Client */