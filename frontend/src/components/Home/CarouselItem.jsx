import React from 'react'
import PropTypes from 'prop-types'
const CarouselItem = ({ image, title }) => {
  return (
    <div className='flex flex-col items-center justify-center'>
      <img
        className='h-[10rem] w-[10rem] rounded-full object-cover object-center lg:h-[14rem] lg:w-[14rem]'
        src={image}
      />
      <span className='py-5 text-xl font-semibold text-gray-400'>{title}</span>
    </div>
  )
}
CarouselItem.propTypes = {
  image: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
}
export default CarouselItem
