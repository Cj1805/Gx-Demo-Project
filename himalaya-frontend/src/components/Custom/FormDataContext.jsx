import React,{createContext, useContext, useReducer} from 'react'


const FormDataContext = createContext();

const FormReducer = (state , action) => {
    switch(action.type){
        case 'SET': return {...state, ...action.payLoad};
        default: return state;
    }
};

const FormDataProvider = ({children})=>{
    const [formData, dispatch] = useReducer(FormReducer,{});

    return(
        <FormDataContext.Provider value={{formData, dispatch}}>
        {children}
        </FormDataContext.Provider>
    )
}

const useFormData = () =>{
    const context = useContext(FormDataContext);
    if(!context){
        throw new Error("Use FormData Provider")
    }
    return context;
}

export {FormDataProvider, useFormData};