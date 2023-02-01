package com.example.cinema.entity.allGallery

import com.example.cinema.entity.galleryFilm.GalleryItem

data class GalleryAllFilm(
    var still: List<GalleryItem>?,
    var shooting: List<GalleryItem>?,
    var poster: List<GalleryItem>?,
    var fanArt: List<GalleryItem>?,
    var promo: List<GalleryItem>?,
    var concept: List<GalleryItem>?,
    var wallpaper: List<GalleryItem>?,
    var cover: List<GalleryItem>?,
    var screenshot: List<GalleryItem>?
)
