import { IconButton } from '@mui/material'
import React from 'react'
import SearchIcon from '@mui/icons-material/Search'
import Avatar from '@mui/material/Avatar'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart'
import Badge from '@mui/material/Badge'
import './Navbar.css'

const Navbar = () => {
  return (
    <div className='z-50 flex justify-between bg-[#81C784] px-5 py-[.8rem] lg:px-20'>
      <div className='flex cursor-pointer items-center lg:mr-10'>
        <li className='logo text-2xl font-semibold text-gray-300'>Xuwei Food</li>
      </div>
      <div className='flex items-center space-x-2 lg:space-x-10'>
        <div className=''>
          <IconButton>
            <SearchIcon
              sx={{
                fontSize: '1.5rem',
              }}
            />
          </IconButton>
        </div>
        <div className=''>
          <Avatar
            sx={{
              bgcolor: 'white',
              color: 'red',
            }}>
            U
          </Avatar>
        </div>
        <div className=''>
          <IconButton>
            <Badge color='secondary' badgeContent={3}>
              <ShoppingCartIcon
                sx={{
                  fontSize: '1.5rem',
                }}
              />
            </Badge>
          </IconButton>
        </div>
      </div>
    </div>
  )
}

export default Navbar
