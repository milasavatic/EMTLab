import React, {useState} from 'react';
import {Box, Button, CircularProgress} from "@mui/material";
import BooksGrid from "../../components/books/BooksGrid/BooksGrid.jsx";
import BooksTable from "../../components/books/BooksTable/BooksTable.jsx";
import useBooks from "../../../hooks/useBooks.js";
import "./BooksPage.css";
import AddBookDialog from "../../components/books/AddBookDialog/AddBookDialog.jsx";
import { useViewMode } from '../../../context/ViewModeContext';
import ViewListIcon from '@mui/icons-material/ViewList';
import ViewModuleIcon from '@mui/icons-material/ViewModule';

const BooksPage = () => {
    const {books, loading, onAdd, onEdit, onDelete} = useBooks();
    const [addBookDialogOpen, setAddBookDialogOpen] = useState(false);
    const { viewMode, setViewMode } = useViewMode();

    const toggleView = () => {
        setViewMode(prev => (prev === 'card' ? 'table' : 'card'));
    };

    return (
        <>
            <Box className="books-box">
                {loading && (
                    <Box className="progress-box">
                        <CircularProgress/>
                    </Box>
                )}
                {!loading &&
                    <>
                        <Box sx={{display: "flex", justifyContent: "flex-end", mb: 2}}>
                            <Button
                                variant="contained"
                                startIcon={viewMode === 'card' ? <ViewListIcon /> : <ViewModuleIcon />}
                                onClick={toggleView}
                                sx={{ mr: 2 }}
                            >
                                {viewMode === 'card' ? 'Table' : 'Card'} View
                            </Button>
                            <Button variant="contained" color="primary" onClick={() => setAddBookDialogOpen(true)}>
                                Add Book
                            </Button>
                        </Box>

                        {viewMode === 'card' ? (
                            <BooksGrid books={books} onEdit={onEdit} onDelete={onDelete} />
                        ) : (
                            <BooksTable books={books} onEdit={onEdit} onDelete={onDelete} />
                        )}
                    </>}
            </Box>
            <AddBookDialog
                open={addBookDialogOpen}
                onClose={() => setAddBookDialogOpen(false)}
                onAdd={onAdd}
            />
        </>
    );
};

export default BooksPage;
