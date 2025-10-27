import React from 'react'
import './Home.css'
import MulitItemCarousel from './MulitItemCarousel'
import RestaurantCard from '../Restaurant/RestaurantCard'

const restaurants = [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]

const Home = () => {
  return (
    <div className='pb-10'>
      <section className='banner relative -z-50 flex flex-col items-center justify-center'>
        <div className='z-10 w-[50vw] text-center'>
          <p className='z-10 py-5 text-2xl font-bold lg:text-6xl'>Xuwei Food</p>
          <p className='z-10 text-xl text-gray-300 lg:text-4xl'>
            Taste the Convenience : Food,Fast and Delivered.
          </p>
        </div>
        <div className='cover absolute left-0 right-0 top-0'></div>
        <div className='fadout'></div>
      </section>

      <section className='p-10 lg:px-20 lg:py-10'>
        <p className='py-3 pb-10 text-left text-2xl font-semibold text-gray-300'>
          Top Meal
        </p>
        <MulitItemCarousel />
      </section>

      <section className='px-5 pt-10 lg:px-20'>
        <h1 className='pb-8 text-left text-2xl font-semibold text-gray-300'>
          Order From Our Handpicked Favorites
        </h1>
        <div className='flex flex-wrap items-center justify-center gap-5'>
          {restaurants.map((item, index) => (
            <RestaurantCard key={index} item={item} />
          ))}
        </div>
      </section>
    </div>
  )
}

export default Home
