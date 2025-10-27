import React from 'react'
import 'slick-carousel/slick/slick.css'
import 'slick-carousel/slick/slick-theme.css'
import Slider from 'react-slick'
import { topMeals } from './topMeal'
import CarouselItem from './CarouselItem'

const MulitItemCarousel = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 6,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
  }

  return (
    <div className='py-10'>
      <Slider {...settings}>
        {topMeals.map(item => (
          <CarouselItem
            key={item.title}
            image={item.image}
            title={item.title}
          />
        ))}
      </Slider>
    </div>
  )
}

export default MulitItemCarousel
