package com.valimade.geofinder.domain.exception

class PermissionNotGrantedException(
    message: String = "Нет разрешения на получение геолокации"
) : Exception(message)