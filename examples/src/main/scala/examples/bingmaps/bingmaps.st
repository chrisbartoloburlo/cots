S_bingmaps=!DefaultApi.locationsQueryGet(key: String(getKey), query: String(getQuery)).
                ?C200(location: Location).
           !DefaultApi.imageryMetadataImagerySetCenterPointGet(key, imagerySet: String(constant "Birdseye"), centerPoint: String(constant "47.610,-122.107")).
                ?C200(imageryMetadata: ImageryMetadata)