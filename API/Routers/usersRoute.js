const express = require('express');
const router = express.Router();

const UserController = require('../Controllers/usersController');
const authHelper = require('../Helpers/AuthHelper');

router.get('/users', UserController.getAllUsers);
router.get('/user/:id', UserController.getUserById);
router.put('/update-user/:id', UserController.updateUser);
router.delete('/delete-user/:id', UserController.deleteUser);

module.exports = router;