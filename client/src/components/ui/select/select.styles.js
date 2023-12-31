export const selectStyles = {
    option: (provided, state) => ({
      ...provided,
      // fontSize: '1rem',
      fontSize: "0.875rem",
      color: "#6B7280",
      paddingLeft: 16,
      paddingRight: 16,
      paddingTop: 12,
      paddingBottom: 12,
      cursor: "pointer",
      borderBottom: "1px solid #E5E7EB",
      backgroundColor: state.isSelected
        ? "#E5E7EB"
        : state.isFocused
        ? "#F9FAFB"
        : "#ffffff",
    }),
    control: (_, state) => ({
      display: "flex",
      alignItems: "center",
      minHeight: 40,
      // backgroundColor: '#F3F4F6',
      backgroundColor: "#ffffff",
      borderRadius: 5,
      border: "1px solid #D1D5DB",
      borderColor: state.isFocused ? "rgb(var(--color-accent-500))" : "#D1D5DB",
      boxShadow:
        state.menuIsOpen &&
        "0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)",
    }),
    indicatorSeparator: () => ({
      display: "none",
    }),
    dropdownIndicator: (provided, state) => ({
      ...provided,
      color: state.isFocused ? "#9CA3AF" : "#cccccc",
      "&:hover": {
        color: "#9CA3AF",
      },
    }),
    clearIndicator: (provided, state) => ({
      ...provided,
      color: state.isFocused ? "#9CA3AF" : "#cccccc",
      padding: 0,
      cursor: "pointer",
  
      "&:hover": {
        color: "#9CA3AF",
      },
    }),
    menu: (provided) => ({
      ...provided,
      borderRadius: 5,
      border: "1px solid #E5E7EB",
      boxShadow:
        "0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)",
    }),
    valueContainer: (provided, _) => ({
      ...provided,
      paddingLeft: 16,
    }),
    singleValue: (provided, _) => ({
      ...provided,
      // fontSize: '1rem',
      fontSize: "0.875rem",
      color: "#4B5563",
    }),
    multiValue: (provided, _) => ({
      ...provided,
      backgroundColor: "rgb(var(--color-accent-400))",
      borderRadius: 9999,
      overflow: "hidden",
      boxShadow:
        "0 0px 3px 0 rgba(0, 0, 0, 0.1), 0 0px 2px 0 rgba(0, 0, 0, 0.06)",
    }),
    multiValueLabel: (provided, _) => ({
      ...provided,
      paddingLeft: 10,
      fontSize: "0.875rem",
      color: "#ffffff",
    }),
    multiValueRemove: (provided, _) => ({
      ...provided,
      paddingLeft: 0,
      paddingRight: 8,
      color: "#ffffff",
      cursor: "pointer",
  
      "&:hover": {
        backgroundColor: "rgb(var(--color-accent-300))",
        color: "#F3F4F6",
      },
    }),
    placeholder: (provided, _) => ({
      ...provided,
      fontSize: "0.875rem",
      color: "rgba(107, 114, 128, 0.7)",
    }),
    noOptionsMessage: (provided, _) => ({
      ...provided,
      fontSize: "0.875rem",
      color: "rgba(107, 114, 128, 0.7)",
    }),
  };
  