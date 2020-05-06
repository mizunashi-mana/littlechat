package com.example.littlechat.server.support.proto

import com.example.littlechat.proto.Common

object ResponseCommon {
    fun success(): Common.ResponseCommon {
        return Common.ResponseCommon.newBuilder()
            .setStatusCode(Common.StatusCode.SUCCESS)
            .build()
    }

    fun internalError(message: String): Common.ResponseCommon {
        return Common.ResponseCommon.newBuilder()
            .setStatusCode(Common.StatusCode.INTERNAL_ERROR)
            .setDetailedError(
                Common.DetailedError.newBuilder()
                    .setMessage(message)
                    .build()
            )
            .build()
    }

    fun unknownError(message: String): Common.ResponseCommon {
        return Common.ResponseCommon.newBuilder()
            .setStatusCode(Common.StatusCode.UNKNOWN_ERROR)
            .setDetailedError(
                Common.DetailedError.newBuilder()
                    .setMessage(message)
                    .build()
            )
            .build()
    }
}
