import { createContext, useContext, useState } from 'react';

const ViewModeContext = createContext();

export const ViewModeProvider = ({ children }) => {
    const [viewMode, setViewMode] = useState('card'); // 'card' or 'table'

    return (
        <ViewModeContext.Provider value={{ viewMode, setViewMode }}>
            {children}
        </ViewModeContext.Provider>
    );
};

export const useViewMode = () => useContext(ViewModeContext);
