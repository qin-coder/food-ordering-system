import React from 'react'
import { Card, Chip, IconButton } from '@mui/material'
import FavoriteIcon from '@mui/icons-material/Favorite'
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder'
import PropTypes from 'prop-types'

const RestaurantCard = ({
  isClickable = true,
  isOpen = true,
  isFavorite = false,
}) => {
  return (
    <Card className='m-5 w-[28rem]'>
      <div
        className={`${isClickable ? 'cursor-pointer' : 'cursor-not-allowed'} relative`}>
        <img
          className='h-[10rem] w-full rounded-t-md object-cover'
          src='https://images.pexels.com/photos/260922/pexels-photo-260922.jpeg'
          alt='Restaurant Cover'
        />

        <Chip
          size='small'
          className='absolute left-2 top-2'
          color={isOpen ? 'success' : 'error'}
          label={isOpen ? 'Open' : 'Closed'}
        />
      </div>

      <div className='textPart w-full justify-between p-4 lg:flex'>
        <div className='space-y-1'>
          <p className='text-lg font-semibold text-gray-300'>
            Michael Fast Food
          </p>
          <p className='text-sm text-gray-500'>Welcome to our restaurant</p>
        </div>

        <div>
          <IconButton>
            {isFavorite ? <FavoriteIcon /> : <FavoriteBorderIcon />}
          </IconButton>
        </div>
      </div>
    </Card>
  )
}

RestaurantCard.propTypes = {
  isClickable: PropTypes.bool,
  isOpen: PropTypes.bool,
  isFavorite: PropTypes.bool,
}

export default RestaurantCard
