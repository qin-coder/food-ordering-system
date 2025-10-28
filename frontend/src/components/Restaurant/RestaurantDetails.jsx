import React from 'react'
import { Grid } from '@mui/material'
import LocationOnIcon from '@mui/icons-material/LocationOn'
import CalendarTodayIcon from '@mui/icons-material/CalendarToday'
const RestaurantDetails = () => {
  return (
    <div className='px-5 lg:px-20'>
      <section className='py-10'>
        <h3 className='mt-10 py-2 text-gray-500'>Home/Greece/Greece Food/3</h3>
        <div>
          <Grid container spacing={2}>
            <Grid item xs={12}>
              <img
                className='w-fullobject-cover h-[40vh]'
                src='https://images.pexels.com/photos/1307698/pexels-photo-1307698.jpeg'
                alt=''
              />
            </Grid>
            <Grid item xs={12} lg={6}>
              <img
                className='w-fullobject-cover h-[40vh]'
                src='https://images.pexels.com/photos/735869/pexels-photo-735869.jpeg'
                alt=''
              />
            </Grid>
            <Grid item xs={12} lg={6}>
              <img
                className='w-fullobject-cover h-[40vh]'
                src='https://images.pexels.com/photos/460537/pexels-photo-460537.jpeg'
                alt=''
              />
            </Grid>
          </Grid>
        </div>
        <div className='pb-5 pt-3'>
          <h1 className='text-left text-4xl font-semibold'>
            Greece Traditonal Food
          </h1>
          <p className='flex items-center gap-3 text-xl text-gray-500'>
            <LocationOnIcon />
            <span>Mainstraße 1, 12345 Berlin</span>
          </p>

          <p className='flex items-center gap-3 text-xl text-gray-500'>
            <CalendarTodayIcon />
            <span>Montag bis Samstag: 18.00 - 00.00 Uhr</span>
          </p>
        </div>
      </section>
    </div>
  )
}

export default RestaurantDetails
