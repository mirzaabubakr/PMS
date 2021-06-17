const express = require('express');
const router = express.Router();

const ProjectController = require('../Controllers/projectController');
// const authHelper = require('../Helpers/AuthHelper');

router.post('/create-project', ProjectController.CreateProject);
router.get('/get-all-projects', ProjectController.getAllProjects);
router.put('/update-project/:id', ProjectController.updateProject);
router.delete('/delete-project/:id', ProjectController.deleteProject);

module.exports = router;